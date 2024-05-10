#!/bin/bash

echo "
+---------------------+
| DEPLOY DE LA LAMBDA |
+---------------------+
| 1) Develop          |
| 2) QA               |
| 3) Production       |
| 4) Salir            |
+---------------------+
"

echo "Selecciona tu opcion"
read -r option

case $option in
    1)
        echo "*************************************************************************"
        echo "Instalando lambda en AWS [Develop]"
        echo "*************************************************************************"
        LAMBDA_ROLE_ARN=arn:aws:iam::167591969773:role/lambda-dev-role
    ;;
    2)
        echo "*************************************************************************"
        echo "Instalando lambda en AWS [QA]"
        echo "*************************************************************************"
        LAMBDA_ROLE_ARN=arn:aws:iam::731901417121:role/lambda-role
    ;;
    3)
        echo "*************************************************************************"
        echo "Instalando lambda en AWS [Production]"
        echo "*************************************************************************"
        echo "aun no configurado..."
        exit 0
    ;;
    4)
        echo "Saliendo..."
        exit 0
    ;;
    *)
        echo "Opcion no valida"
        exit 1
    ;;
esac

function cmd_create() {
    echo Creating function
    set -x
    aws lambda create-function \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE} \
    --handler ${HANDLER} \
    --runtime ${RUNTIME} \
    --role ${LAMBDA_ROLE_ARN} \
    --timeout 15 \
    --memory-size 256 \
    ${LAMBDA_META}
    # Enable and move this param above ${LAMBDA_META}, if using AWS X-Ray
    #    --tracing-config Mode=Active \
}

function cmd_delete() {
    echo Deleting function
    set -x
    aws lambda delete-function --function-name ${FUNCTION_NAME}
}

function cmd_invoke() {
    echo Invoking function
    
    inputFormat=""
    if [ $(aws --version | awk '{print substr($1,9)}' | cut -c1-1) -ge 2 ]; then inputFormat="--cli-binary-format raw-in-base64-out"; fi
    
    set -x
    
    aws lambda invoke response.txt \
    ${inputFormat} \
    --function-name ${FUNCTION_NAME} \
    --payload file://payload.json \
    --log-type Tail \
    --query 'LogResult' \
    --output text |  base64 --decode
    { set +x; } 2>/dev/null
    cat response.txt && rm -f response.txt
}

function cmd_update() {
    echo Updating function
    set -x
    aws lambda update-function-code \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE}
}

FUNCTION_NAME=DropNewLayout
HANDLER=io.quarkus.funqy.lambda.FunqyStreamHandler::handleRequest
RUNTIME=java11
ZIP_FILE=fileb://target/function.zip

function usage() {
    [ "_$1" == "_" ] && echo -e "\nUsage (JVM): \n$0 [create|delete|invoke]\ne.g.: $0 invoke"
    [ "_$1" == "_" ] && echo -e "\nUsage (Native): \n$0 native [create|delete|invoke]\ne.g.: $0 native invoke"
    
    [ "_" == "_`which aws 2>/dev/null`" ] && echo -e "\naws CLI not installed. Please see https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html"
    [ ! -e $HOME/.aws/credentials ] && [ "_$AWS_ACCESS_KEY_ID" == "_" ] && echo -e "\naws configure not setup.  Please execute: aws configure"
    [ "_$LAMBDA_ROLE_ARN" == "_" ] && echo -e "\nEnvironment variable must be set: LAMBDA_ROLE_ARN\ne.g.: export LAMBDA_ROLE_ARN=arn:aws:iam::123456789012:role/my-example-role"
}

if [ "_$1" == "_" ] || [ "$1" == "help" ]
then
    usage
fi

if [ "$1" == "native" ]
then
    RUNTIME=provided
    ZIP_FILE=fileb://target/function.zip
    FUNCTION_NAME=DropNewLayoutNative
    LAMBDA_META="--environment Variables={DISABLE_SIGNAL_HANDLERS=true}"
    shift
fi

while [ "$1" ]
do
    eval cmd_${1}
    { set +x; } 2>/dev/null
    shift
done


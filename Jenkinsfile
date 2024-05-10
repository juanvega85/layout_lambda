def AGENT_LABEL = null

node('master') {
    stage('check agent') {
        checkout scm
        if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME.startsWith('release/') || env.BRANCH_NAME == 'feature/jenkins') {
            AGENT_LABEL = 'spot-fleet'
        } else {
            AGENT_LABEL = 'master'
        }
    }
}

pipeline {
    agent { label AGENT_LABEL }

    options {
        // Evita que se ejecute mas de un job concurrente para el mismo branch.
        disableConcurrentBuilds()
    }

    environment {

        AWS_PROFILE_DEV = 'aws-drop-dev'
        AWS_PROFILE_QA = 'aws-drop-qa'
        AWS_PROFILE_PRD = 'aws-drop-prod'
        AWS_REGION = 'us-east-1'

        ROLE_ARN_DEV = 'arn:aws:iam::167591969773:role/lambda-dev-role'
        ROLE_ARN_QA = 'arn:aws:iam::731901417121:role/lambda-role'
        ROLE_ARN_PRD = 'arn:aws:iam::352023330825:role/lambda-role'

        // Se debe cambiar Nombre de la función para que sea el nombre del lambda en aws.
        FUNCTION_NAME = 'DropNewLayoutNative'

        AMBIENTE_CONSTRUCCION = obtenerEntorno(env.BRANCH_NAME)
    }

    stages {
        stage('check configuraciones') {
            steps {
                echo "AGENT           : ${AGENT_LABEL}"
                echo "BRANCH          : ${env.BRANCH_NAME}"
                echo "AMBIENTE        : ${AMBIENTE_CONSTRUCCION}"

                echo "AWS_PROFILE_QA  : ${AWS_PROFILE_QA}"
                echo "AWS_PROFILE_PRD : ${AWS_PROFILE_PRD}"
                echo "AWS_REGION      : ${AWS_REGION}"

                echo "FUNCTION_NAME   : ${FUNCTION_NAME}"

                sh "aws --version"
                sh "java -version"
                sh "mvn -v"
            }
        }

        stage('Construcción Quarkus Native') {
            when {
                anyOf {
                    branch 'master';
                    branch 'develop';
                    branch 'feature/jenkins'
                    branch 'release/**'
                }
            }
            environment {
                AWS_PROFILE_JENKINS = obtenerPerfilAWS(AMBIENTE_CONSTRUCCION)
            }
            steps {
                echo "Construyendo... ${env.BUILD_ID} on ${env.JENKINS_URL}${env.JOB_NAME}"
                echo "AWS_PROFILE: ${AWS_PROFILE_JENKINS}"

                withAWS(credentials: "${AWS_PROFILE_JENKINS}", region: "${AWS_REGION}") {
                    sh "mvn clean package -Dquarkus.package.type=native -Dmaven.test.skip=true -Dquarkus.profile=${AMBIENTE_CONSTRUCCION}"
                }
            }
        }

        stage('Instalacion') {
            when {
                anyOf {
                    branch 'master';
                    branch 'develop';
                    branch 'feature/jenkins'
                    branch 'release/**'
                }
            }
            environment {
                AWS_PROFILE_JENKINS = obtenerPerfilAWS(AMBIENTE_CONSTRUCCION)
                AWS_ROLE_JENKINS = obtenerRolAWS(AMBIENTE_CONSTRUCCION)
            }

            steps {
                echo "Instalando... ${env.BUILD_ID} on ${env.JENKINS_URL}${env.JOB_NAME}"
                echo "AWS_PROFILE: ${AWS_PROFILE_JENKINS}"

                withAWS(credentials: "${AWS_PROFILE_JENKINS}", region: "${AWS_REGION}") {
                    script {
                        def procedimiento = "update"
                        if (!existeFuncionLambda()) {
                            procedimiento = "create"
                        }

                        sh """
                            export LAMBDA_NAME=${FUNCTION_NAME}
                            export QUARKUS_PROFILE=${AMBIENTE_CONSTRUCCION}
                            export LAMBDA_ROLE_ARN=${AWS_ROLE_JENKINS}
                            ./deploy-ic.sh native ${procedimiento}
                        """
                    }
                }
            }
        }
    }
}

def obtenerEntorno(branch) {
    def res = 'dev'
    if (branch.indexOf("release") >= 0 || branch.indexOf("release") >= 0) {
        res = 'qa'
    } else if (branch == 'master') {
        res = 'prod'
    }
    return res;
}

def existeFuncionLambda() {
    def existe = false;
    try {
        echo "verificando si existe la funcion."
        sh "aws lambda get-function --function-name ${FUNCTION_NAME}"
        existe = true;
    } catch (Exception e) {
        echo "funcion no existe se intentara creacion."
    }
    return existe;
}

def obtenerPerfilAWS(ambiente) {
    def perfil = AWS_PROFILE_DEV;
    if (ambiente == 'qa') {
        perfil = AWS_PROFILE_QA;
    } else if (ambiente == 'prod') {
        perfil = AWS_PROFILE_PRD;
    }
    return perfil;
}

def obtenerRolAWS(ambiente) {
    def rol = ROLE_ARN_DEV;
    if (ambiente == 'qa') {
        rol = ROLE_ARN_QA;
    } else if (ambiente == 'prod') {
        rol = ROLE_ARN_PRD;
    }
    return rol;
}

def getSonarOps(ambiente, branch) {
    def opciones = "";
    if (ambiente != 'produccion') {
        opciones = "-Dsonar.branch.name=" + branch;
    }
    return opciones;
}
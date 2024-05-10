#!/bin/bash

mainmenu() {
    echo "
+----------------------------------+
| MODO DE COMPILACION DE LA LAMBDA |
+----------------------------------+
| 1) Docker Develop                |
| 2) Docker QA                     |
| 3) Docker Production             |
| 4) Develop                       |
| 5) QA                            |
| 6) Production                    |
| 7) Salir                         |
+----------------------------------+
    "
    
    echo "Selecciona tu opcion"
    read -r option
    case $option in
        1)
            echo "*************************************************************************"
            echo "Este script solo compila, no instala el componente [Develop-Docker]"
            echo "*************************************************************************"
            mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker -Dquarkus.profile=dev
        ;;
        2)
            echo "*************************************************************************"
            echo "Este script solo compila, no instala el componente [QA-Docker]"
            echo "*************************************************************************"
            mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker -Dquarkus.profile=qa
        ;;
        3)
            echo "************************************************************************+"
            echo "Este script solo compila, no instala el componente [Production-Docker]"
            echo "*************************************************************************"
            mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker -Dquarkus.profile=prod
        ;;
        4)
            echo "************************************************************************+"
            echo "Este script solo compila, no instala el componente [Develop]"
            echo "*************************************************************************"
            mvn clean install -Dnative -Dquarkus.native.container-build=true -Dmaven.test.skip=true -Dquarkus.profile=dev
        ;;
        5)
            echo "************************************************************************+"
            echo "Este script solo compila, no instala el componente [QA]"
            echo "*************************************************************************"
            mvn clean install -Dnative -Dquarkus.native.container-build=true -Dmaven.test.skip=true -Dquarkus.profile=qa
        ;;
        6)
            echo "************************************************************************+"
            echo "Este script solo compila, no instala el componente [Production]"
            echo "*************************************************************************"
            mvn clean install -Dnative -Dquarkus.native.container-build=true -Dmaven.test.skip=true -Dquarkus.profile=prod
        ;;
        7)
            echo "Saliendo..."
            exit 0
        ;;
        *)
            echo "Opcion no valida"
            exit 1
        ;;
    esac
}

mainmenu
pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'jansori_credential'
        SOURCE_CODE_URL = 'https://lab.ssafy.com/s09-webmobile2-sub2/S09P12A606.git'
        RELEASE_BRANCH = 'release-test'
    }
    stages {
        stage('Init') {
            steps {
                echo 'clear'
                sh 'docker stop $(docker ps -aq)'
                sh 'docker rm $(docker ps -aq)'
                deleteDir()
            }
        }

        stage('clone') {
            steps {
                git url: "$SOURCE_CODE_URL",
                    branch: "$RELEASE_BRANCH",
                    credentialsId: "$SOURCECODE_JENKINS_CREDENTIAL_ID"
                sh "ls -al"
            }
        }

        stage('frontend dockerizing') {
            steps {
                sh "docker build -t jansori/frontend ./frontend"
            }
        }

        stage('backend dockerizing') {
            steps {
                sh "pwd"
                dir("./backend"){
                    sh "pwd"

                    sh "gradle clean"
                    sh "gradle bootJar"

                    sh "docker build -t jansori/backend ."
                }
            }
        }

        stage('deploy') {
            steps {
                sh '''
                  docker run -d -p 3000:3000 jansori/frontend

                  docker run -d -p 8080:8080 jansori/backend
                '''
            }
        }
    }
}
pipeline {
    agent any

    stages {
        stage('SCM') {
            steps {
                // get the latest changes from github repository
                git 'https://github.com/Siddhantkpatil/dittis-website-demo_pac.git'
            }
        }

        stage('build docker image') {
            steps {
                sh 'docker image build -t siddhant295/jpac_httpd .'
            }
        }

        stage('docker login') {
            steps {
                sh 'echo "docker_token"|docker login -u siddhant295 --password-stdin'
            }
        }

        stage('push docker image') {
            steps {
                sh 'docker image push siddhant295/jpac_httpd'
            }
        }

        stage('reload the service') {
            steps {
                sh 'docker service update --force --image siddhant295/jpac_httpd jpac'
            }
        }
    }
}
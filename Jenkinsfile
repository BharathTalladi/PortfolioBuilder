pipeline {
    agent any
    tools {
        maven 'maven_3_9_5'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BharathTalladi/PortfolioBuilder/tree/master/employee-recurring-plans']])
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Build Backend Docker Image') {
            steps {
                script {
                    // Build backend Docker image
                    dir('employee-recurring-plans') {
                        bat 'docker build -t talladi412/portfolio-builder-backend .'
                    }
                }
            }
        }
        stage('Push Backend Image to Hub') {
            steps {
                script {
                    // Log in to Docker Hub
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u talladi412 -p %dockerhubpwd%'
                    }
                    // Tag and push backend Docker image
                    bat 'docker tag talladi412/portfolio-builder-backend talladi412/portfolio-builder-backend:1.0.0'
                    bat 'docker push talladi412/portfolio-builder-backend:1.0.0'
                }
            }
        }
        stage('Build Frontend Docker Image') {
            steps {
                script {
                    // Build frontend Docker image
                    dir('employee-recurring-plans-frontend') {
                        bat 'docker build -t talladi412/portfolio-builder-frontend .'
                    }
                }
            }
        }
        stage('Push Frontend Image to Hub') {
            steps {
                script {
                    // Log in to Docker Hub
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u talladi412 -p %dockerhubpwd%'
                    }
                    // Tag and push frontend Docker image
                    bat 'docker tag talladi412/portfolio-builder-frontend talladi412/portfolio-builder-frontend:1.0.0'
                    bat 'docker push talladi412/portfolio-builder-frontend:1.0.0'
                }
            }
        }
        /*stage('Deploy to K8s') {
            steps {
                script {
                    kubernetesDeploy(kubeconfigId: 'k8sconfigpwd', yamlFile: 'app-deployment.yml')
                }
            }
        }*/
    }
}
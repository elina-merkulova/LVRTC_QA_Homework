pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3'
        jdk 'OpenJDK 11'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean compile -DskipTests'
                }
            }
        }

        stage('Run Acceptance Tests') {
            steps {
                script {
                    sh 'mvn verify -Pacceptance-tests'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Acceptance tests have passed.'
        }
        failure {
            echo 'Acceptance tests have failed.'
        }
    }
}
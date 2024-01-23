pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                script {
                    // Run clean phase
                    bat 'mvn clean'
                }
            }
        }

        stage('Validate') {
            steps {
                script {
                    // Run validate phase
                    bat 'mvn validate'
                }
            }
        }

        stage('Compile') {
            steps {
                script {
                    // Run compile phase
                    bat 'mvn compile'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run test phase
                    bat 'mvn test'
                }
            }

            post {
                success {
                    // If Maven was able to run the tests, even if some failed,
                    // record the test results and archive the HTML report.
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: false,
                        reportDir: 'target/surefire-reports/',
                        reportFiles: 'emailable-report.html',
                        reportName: 'HTML Report',
                        reportTitles: '',
                        useWrapperFileDirectly: true
                    ])
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Run package phase
                    bat 'mvn package'
                }
            }
        }

        stage('Verify') {
            steps {
                script {
                    // Run verify phase
                    bat 'mvn verify'
                }
            }
        }

        stage('Install') {
            steps {
                script {
                    // Run install phase
                    bat 'mvn install'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Run deploy phase
                    bat 'mvn deploy'
                }
            }
        }
    }
}

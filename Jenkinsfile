pipeline {
    agent any

    stages {
       
        stage('Clean') {
            steps {
                script {
                    // Run clean phase
                    bat 'mvn clean'
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

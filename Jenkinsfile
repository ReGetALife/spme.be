pipeline {
    agent { docker 'maven:3.6.0' }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
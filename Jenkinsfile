pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /home/user/spme/.m2:/root/.m2'
    }

  }
  stages {
    stage('build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('test') {
      steps {
        echo 'todo: add some test'
      }
    }

    stage('deploy') {
      steps {
        echo 'todo: deploy target on server'
      }
    }

  }
}
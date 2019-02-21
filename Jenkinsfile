node('maven') {
    stages {
        stage('checkout') {
            checkout scm
        }

        stage('build') {
            sh 'mvn -B -DskipTests clean package'
        }

        stage('test') {
            sh 'mvn test'
        }
    }
}

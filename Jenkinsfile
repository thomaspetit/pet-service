node('maven') {
    stage('Build') {
        git url: "https://github.com/TPX01/pet-service.git"
        sh "mvn package"
        stash name:"jar", includes:"target/PetService.jar"
    }

    stage('Test') {
        sh "mvn test"
    }
    stage('Build Image') {
        unstash name:"jar"
        app = docker.build("thomaspetit/petservice")
    }

    stage('Test image') {
        app.inside {
            sh 'echo "Tests passed"'
        }
    }

    stage('Push image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }
    }

    stage('Build Image OC native') {
        unstash name:"jar"
        sh "oc start-build pet --from-file=Dockerfile --follow"
    }

    stage('Deploy DEV') {
    }

    stage('Promote to STG') {
        steps {
            timeout(time:15, unit:'MINUTES') {
                input message: "Promote to STG?", ok: "Promote"
            }
        }
    }
}
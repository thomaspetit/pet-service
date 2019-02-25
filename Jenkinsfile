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
    sh "oc start-build pet --from-file=Dockerfile --follow"
  }
  stage('Deploy') {
    openshiftDeploy depCfg: 'pet'
    openshiftVerifyDeployment depCfg: 'pet', replicaCount: 1, verifyReplicaCount: true
  }
  stage('System Test') {
    sh "curl -s http://cart:8080/health | grep 'UP'"
  }
}
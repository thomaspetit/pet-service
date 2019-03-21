pipeline {
    agent {
        label 'maven'
    }

    environment {
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
    }

    stages {
        stage('Build') {
            steps {
                git url: "https://github.com/TPX01/pet-service.git"
                sh "mvn package"
                stash name:"jar", includes:"target/${IMAGE}.jar"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('Create Image Builder') {
            when {
                expression {
                    openshift.withCluster() {
                        return !openshift.selector("bc", "${IMAGE}").exists();
                    }
                }
            }
            steps {
                script {
                    openshift.withCluster() {
                        openshift.newBuild("--name=${IMAGE}", "--image-stream=openjdk:8-jdk-alpine", "--binary")
                    }
                }
            }
        }

        stage('Build Image') {
            steps {
                unstash name:"jar"
                sh "cp target/${IMAGE}.jar target/ROOT.jar"
                script {
                    openshift.withCluster() {
                        openshift.selector("bc", "${IMAGE}").startBuild("--from-file=target/ROOT.jar", "--wait")
                    }
                }
            }
        }

        stage('Tag Image') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                          openshift.tag("${IMAGE}:latest", "${IMAGE}-staging:latest")
                        }
                    }
                }
            }
        }

        stage('Deploy DEV') {
            steps {
                timeout(time:15, unit:'MINUTES') {
                    input message: "Promote to DEV", ok: "Promote"
                }
            }
        }
    }
}
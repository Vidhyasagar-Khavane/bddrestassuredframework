pipeline{
    agent any

    parameters{
        choice(
                name:'TAGS',
                choices:['@smoke','@regression','@all'],
                description:'Cucumber tags to run'
        )
        choice(
                name:'ENV',
                choices:['dev','stage','prod'],
                description:'Test Environments'
        )
    }

    stages{
        stage("Checkout"){
            steps{
                git branch: 'master', url:'https://github.com/Vidhyasagar-Khavane/bddrestassuredframework.git'
            }
        }
        stage("Build"){
            steps{
                script{
                    if(isUnix){
                        sh 'mvn clean install'
                    }else{
                        bat 'mvn clean install'
                    }
                }
            }
        }
        stage('Test'){
            steps{
                script{
                    def testCommand="mvn test -Dcucumber.filter.tags=\"${params.TAGS}\" -Denv=${params.ENV}"
                    if(isUnix){
                        sh testCommand
                    }else{
                        bat testCommand
                    }
                }
            }
        }
    }
    post{
        always{
            cucumber buildStatus:'UNSTABLE',
                fileIncludePattern:'**/cucumber.json',
                jsonReportDirectory:'target'
            junit '**/target/surefire-reports/*.xml'

        }
    }
}

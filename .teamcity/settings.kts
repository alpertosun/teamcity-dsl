import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.build
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2021.1"

project {
    var projectId = "TestProject"
    name = "Test Project"
    vcsRoot {
        id = "VcsRoot"
        name = "VCS Root"
        type = "git"
        param("url", "https://github.com/alpertosun/teamcity-dsl")
        param("branch", "master")
    }

    buildType {
        id = "ExampleBuild"
        name = "Example Build"
        vcs {
            root("VcsRoot")
        }

        steps {
            script {
                name = "Compile and Build"
                scriptContent = """
                #!/bin/bash
                echo "Starting build..."
                """
            }
        }

        triggers {
            vcs {
                branchFilter = """
                    +:*
                """
            }
        }
    }
}

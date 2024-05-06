import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2021.1"

project {
    name = "Example Project"
    id = Id("ExampleProject")

    vcsRoot {
        id = Id("ExampleVcsRoot")
        name = "Example VCS Root"
        type = "git"
        param("url", "https://github.com/example/my-repo.git")
        param("branch", "refs/heads/master")
    }

    buildType {
        id = Id("ExampleBuild")
        name = "Example Build"
        vcs {
            root(Id("ExampleVcsRoot"))
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
                branchFilter = "+:<default>"
            }
        }
    }
}

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

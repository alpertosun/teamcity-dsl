import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
version = "2019.2"
project {
    vcsRoot(Sources)
    buildType(Build)
    buildType(Test)
    sequential {
        buildType(Build)
        buildType(Test, options = { runOnSameAgent = true })
    }
}

object Build : BuildType({
    name = "Build"
    vcs {
        root(Sources)
    }

    steps {
        maven {
            goals = "package"
            runnerArgs = "-DskipTests"
        }
    }
})

object Test : BuildType({
    name = "Test"
    buildNumberPattern = "${Build.depParamRefs.buildNumber}"

    vcs {
        root(Sources)
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

})

object Sources : GitVcsRoot({
    name = "Sources"
    url = DslContext.getParameter("repoUrl")
})

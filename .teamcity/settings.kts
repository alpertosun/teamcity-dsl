import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.*
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot
version = "2023.11"
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
            scriptContent = """echo "Hello world!""""
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

package plugins

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.kotlin.dsl.apply

apply(plugin = "com.github.ben-manes.versions")

fun isNonStable(version: String): Boolean {
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return !regex.matches(version)
}

// run with: ./gradlew dependencyUpdates
tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
//    checkForGradleUpdate = true
    resolutionStrategy {
        componentSelection {
            all {
                // to accept nonStable upgrades of non stable versions we can add this check
                // to the if: && !isNonStable(currentVersion)
                if (isNonStable(candidate.version)) {
                    reject("Release candidate")
                }
            }
        }
    }
}
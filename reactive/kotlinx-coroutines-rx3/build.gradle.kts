import org.jetbrains.dokka.gradle.DokkaTaskPartial

targetCompatibility = JavaVersion.VERSION_1_8

dependencies {
    api project(':kotlinx-coroutines-reactive')
    testImplementation project(':kotlinx-coroutines-reactive').sourceSets.test.output
    testImplementation "org.reactivestreams:reactive-streams-tck:$reactive_streams_version"
    api "io.reactivex.rxjava3:rxjava:$rxjava3_version"
}

compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType(DokkaTaskPartial.class) {
    dokkaSourceSets.configureEach {
        externalDocumentationLink {
            url = new URL('https://reactivex.io/RxJava/3.x/javadoc/')
            packageListUrl = projectDir.toPath().resolve("package.list").toUri().toURL()
        }
    }
}

task testNG(type: Test) {
    useTestNG()
    reports.html.destination = file("$buildDir/reports/testng")
    include '**/*ReactiveStreamTckTest.*'
    // Skip testNG when tests are filtered with --tests, otherwise it simply fails
    onlyIf {
        filter.includePatterns.isEmpty()
    }
    doFirst {
        // Classic gradle, nothing works without doFirst
        println "TestNG tests: ($includes)"
    }
}

test {
    dependsOn(testNG)
    reports.html.destination = file("$buildDir/reports/junit")
}

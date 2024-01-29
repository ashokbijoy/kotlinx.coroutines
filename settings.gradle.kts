pluginManagement {
    plugins {
        id "org.openjfx.javafxplugin" version javafx_plugin_version
        id "me.champeau.jmh" version "0.7.2"
    }

    repositories {
        maven { url "https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev/" }
        gradlePluginPortal()
    }
}

rootProject.name = 'kotlinx.coroutines'

def module(String path) {
    int i = path.lastIndexOf('/')
    def name = path.substring(i + 1)
    include(name)
    project(":$name").projectDir = file(path)
}
def prop = System.getProperty("build_snapshot_train")
ext.build_snapshot_train = prop != null && prop != ""
// ---------------------------

include('benchmarks')

include "kotlinx-coroutines-core"

module('kotlinx-coroutines-test')
module('kotlinx-coroutines-debug')
module('kotlinx-coroutines-bom')


module('integration/kotlinx-coroutines-guava')
module('integration/kotlinx-coroutines-jdk8')
module('integration/kotlinx-coroutines-slf4j')
module('integration/kotlinx-coroutines-play-services')

module('reactive/kotlinx-coroutines-reactive')
module('reactive/kotlinx-coroutines-reactor')
module('reactive/kotlinx-coroutines-jdk9')
module('reactive/kotlinx-coroutines-rx2')
module('reactive/kotlinx-coroutines-rx3')
module('ui/kotlinx-coroutines-android')
module('ui/kotlinx-coroutines-android/android-unit-tests')
if (JavaVersion.current().isJava11Compatible()) {
    module('ui/kotlinx-coroutines-javafx')
}
module('ui/kotlinx-coroutines-swing')

grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'

    repositories {
        mavenLocal()
        grailsCentral()
        mavenCentral()
        mavenRepo 'http://clojars.org/repo'
    }

    dependencies {
        runtime 'monetdb:monetdb-jdbc:2.17'
    }

    plugins {
        build(':release:3.1.1', ':rest-client-builder:2.1.1') {
            export = false
        }
    }
}

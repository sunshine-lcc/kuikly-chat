pluginManagement {
    repositories {
//        maven { setUrl("https://maven.aliyun.com/repository/central") }
//        maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
//        maven { setUrl("https://maven.aliyun.com/repository/google") }
//        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { setUrl("https://maven.aliyun.com/repository/public") }
//        maven { setUrl("https://jitpack.io") }
//        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }
//        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter") }

        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
//        maven { setUrl("https://maven.aliyun.com/repository/central") }
//        maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
//        maven { setUrl("https://maven.aliyun.com/repository/google") }
//        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { setUrl("https://maven.aliyun.com/repository/public") }
//        maven { setUrl("https://jitpack.io") }

        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "MyApplication"
include(":androidApp")
include(":shared")
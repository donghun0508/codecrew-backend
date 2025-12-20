import util.springFrameworkBom
import util.implBundle

plugins {
    id("java-conventions")
}

dependencies {
    api(platform(springFrameworkBom()))
}

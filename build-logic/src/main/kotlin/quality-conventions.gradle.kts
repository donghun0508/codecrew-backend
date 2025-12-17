import util.errorProne

plugins {
    id("net.ltgt.errorprone")
}

dependencies {
    errorProne("util.errorprone-core")
    errorProne("nullaway")
}

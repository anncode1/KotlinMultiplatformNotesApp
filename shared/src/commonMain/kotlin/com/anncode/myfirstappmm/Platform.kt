package com.anncode.myfirstappmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
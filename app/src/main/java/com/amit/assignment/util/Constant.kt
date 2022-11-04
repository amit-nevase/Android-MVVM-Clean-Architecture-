package com.amit.assignment.util

object Constant {
    const val BASE_URL = "https://gateway.marvel.com"

    const val MARVEL_API_PUBLIC_KEY = "b91b23455a236a1f1186558f4cc60133"
    const val MARVEL_API_TS = 1
    const val MARVEL_API_HASH = "984c56595d61711f3eb2f8775a2a262c" // md5 (concatenate ts + private key + public key)
}

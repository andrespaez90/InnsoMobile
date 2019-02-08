package com.innso.mobile.extensions

inline fun <T> tryOrDefault(f: () -> T, defaultValue: T): T {
    return try {
        f()
    } catch (e: Exception) {
        defaultValue
    }
}

inline fun <T, U, R> Pair<T?, U?>.biLet(body: (T, U) -> R): R? {
    if (first != null && second != null) {
        return body(first as T, second as U)
    }
    return null
}

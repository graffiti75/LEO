package br.cericatto.leo.model.api

import okhttp3.OkHttpClient

object OkHttpProvider {
    val instance: OkHttpClient = OkHttpClient.Builder().build()
}
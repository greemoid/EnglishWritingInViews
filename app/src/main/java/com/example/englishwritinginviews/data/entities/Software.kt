package com.example.englishwritinginviews.data.entities

data class Software(
    val apiVersion: Int,
    val buildDate: String,
    val name: String,
    val premium: Boolean,
    val premiumHint: String,
    val status: String,
    val version: String
)
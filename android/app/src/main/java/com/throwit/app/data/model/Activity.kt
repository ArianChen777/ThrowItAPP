package com.throwit.app.data.model

data class Activity(
    val id: Long = 0,
    val userId: Long = 0,
    val appName: String = "",
    val activityType: String = "", // entertainment, study, work
    val content: String = "",
    val duration: Int = 0,
    val timestamp: String = "",
    val aiAnalysisResult: String = ""
)
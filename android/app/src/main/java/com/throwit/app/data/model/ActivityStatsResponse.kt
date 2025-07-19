package com.throwit.app.data.model

data class ActivityStatsResponse(
    val totalActivities: Int,
    val entertainmentTime: Int,
    val studyTime: Int,
    val workTime: Int,
    val todayReminders: Int,
    val lastCheckTime: String
)
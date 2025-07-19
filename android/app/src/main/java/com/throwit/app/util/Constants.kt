package com.throwit.app.util

object Constants {
    const val BASE_URL = "http://localhost:8080/"
    const val PREFS_NAME = "throwit_prefs"
    const val KEY_TOKEN = "token"
    const val KEY_USER_ID = "user_id"
    const val KEY_USERNAME = "username"
    
    // Screenshot service
    const val SCREENSHOT_INTERVAL = 5 * 60 * 1000L // 5 minutes
    const val NOTIFICATION_CHANNEL_ID = "throwit_monitoring"
    const val NOTIFICATION_CHANNEL_NAME = "Monitoring Service"
    
    // Activity types
    const val ACTIVITY_TYPE_ENTERTAINMENT = "entertainment"
    const val ACTIVITY_TYPE_STUDY = "study"
    const val ACTIVITY_TYPE_WORK = "work"
}
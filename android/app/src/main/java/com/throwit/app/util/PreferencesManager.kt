package com.throwit.app.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREFS_NAME, 
        Context.MODE_PRIVATE
    )
    
    fun saveToken(token: String) {
        prefs.edit().putString(Constants.KEY_TOKEN, token).apply()
    }
    
    fun getToken(): String? {
        return prefs.getString(Constants.KEY_TOKEN, null)
    }
    
    fun clearToken() {
        prefs.edit().remove(Constants.KEY_TOKEN).apply()
    }
    
    fun saveUserId(userId: Long) {
        prefs.edit().putLong(Constants.KEY_USER_ID, userId).apply()
    }
    
    fun getUserId(): Long {
        return prefs.getLong(Constants.KEY_USER_ID, 0)
    }
    
    fun saveUsername(username: String) {
        prefs.edit().putString(Constants.KEY_USERNAME, username).apply()
    }
    
    fun getUsername(): String? {
        return prefs.getString(Constants.KEY_USERNAME, null)
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
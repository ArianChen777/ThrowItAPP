package com.throwit.app.data.repository

import com.throwit.app.data.api.ApiService
import com.throwit.app.data.model.*
import com.throwit.app.data.api.RegisterRequest
import com.throwit.app.data.api.UpdateProfileRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun login(username: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("登录失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "登录失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun register(username: String, email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.register(RegisterRequest(username, email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("注册失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "注册失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getUserInfo(): Flow<Result<User>> = flow {
        try {
            val response = apiService.getUserInfo()
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("获取用户信息失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "获取用户信息失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun updateProfile(nickname: String, avatar: String): Flow<Result<User>> = flow {
        try {
            val response = apiService.updateProfile(UpdateProfileRequest(nickname, avatar))
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("更新用户信息失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "更新用户信息失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
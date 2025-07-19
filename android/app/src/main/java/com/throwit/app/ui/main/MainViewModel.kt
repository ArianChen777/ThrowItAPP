package com.throwit.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.throwit.app.data.repository.ActivityRepository
import com.throwit.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val isMonitoringEnabled: Boolean = true,
    val lastCheckTime: String = "2分钟前",
    val todayReminders: Int = 3,
    val recordsCount: Int = 6,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        refreshData()
    }
    
    fun toggleMonitoring() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isMonitoringEnabled = !_uiState.value.isMonitoringEnabled
            )
        }
    }
    
    fun refreshData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // 获取活动统计数据
                activityRepository.getActivityStats().collect { result ->
                    result.onSuccess { stats ->
                        _uiState.value = _uiState.value.copy(
                            lastCheckTime = stats.lastCheckTime,
                            todayReminders = stats.todayReminders,
                            recordsCount = stats.totalActivities,
                            isLoading = false
                        )
                    }.onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            error = exception.message,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
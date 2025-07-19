package com.throwit.app.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.throwit.app.R
import com.throwit.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    MainScreenContent(
        isMonitoringEnabled = uiState.isMonitoringEnabled,
        lastCheckTime = uiState.lastCheckTime,
        todayReminders = uiState.todayReminders,
        recordsCount = uiState.recordsCount,
        onToggleMonitoring = { viewModel.toggleMonitoring() },
        onNavigateToRecords = { navController.navigate("records") },
        onNavigateToSettings = { navController.navigate("settings") },
        onNavigateToWhitelist = { navController.navigate("whitelist") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    isMonitoringEnabled: Boolean,
    lastCheckTime: String,
    todayReminders: Int,
    recordsCount: Int,
    onToggleMonitoring: () -> Unit,
    onNavigateToRecords: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToWhitelist: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(scrollState)
    ) {
        // Header
        MainHeader()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Main Status Card
            MainStatusCard(
                isMonitoringEnabled = isMonitoringEnabled,
                lastCheckTime = lastCheckTime,
                todayReminders = todayReminders,
                onToggleMonitoring = onToggleMonitoring
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            // Feature Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.List,
                    title = stringResource(R.string.app_whitelist),
                    subtitle = stringResource(R.string.manage_monitoring_apps),
                    backgroundColor = Color(0xFFF0F0FF),
                    iconColor = Color(0xFF6C5CE7),
                    onClick = onNavigateToWhitelist
                )
                
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Settings,
                    title = stringResource(R.string.settings_center),
                    subtitle = stringResource(R.string.personalized_config),
                    backgroundColor = Color(0xFFF0F0FF),
                    iconColor = Color(0xFF6C5CE7),
                    onClick = onNavigateToSettings
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Usage Statistics Card
            UsageStatisticsCard(
                recordsCount = recordsCount,
                onClick = onNavigateToRecords
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Status Card
            StatusCard()
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun MainHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8E8FF))
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "T",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = stringResource(R.string.main_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(R.string.subtitle),
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }
        
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = IconGray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MainStatusCard(
    isMonitoringEnabled: Boolean,
    lastCheckTime: String,
    todayReminders: Int,
    onToggleMonitoring: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.main_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = stringResource(R.string.main_subtitle),
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Monitoring Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isMonitoringEnabled) Color(0xFFE8F5E8) else Color(0xFFE8E8E8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RemoveRedEye,
                            contentDescription = "Monitor",
                            tint = if (isMonitoringEnabled) GreenSuccess else IconGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = stringResource(R.string.scheduled_monitoring),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isMonitoringEnabled) "监控已开启" else "监控已关闭",
                            fontSize = 12.sp,
                            color = if (isMonitoringEnabled) GreenSuccess else TextSecondary
                        )
                    }
                }
                
                Switch(
                    checked = isMonitoringEnabled,
                    onCheckedChange = { onToggleMonitoring() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = GreenSuccess
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Statistics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem(
                    icon = Icons.Default.Schedule,
                    title = stringResource(R.string.last_check),
                    value = lastCheckTime,
                    valueColor = PrimaryBlue
                )
                
                StatisticItem(
                    icon = Icons.Default.ShowChart,
                    title = stringResource(R.string.today_reminders),
                    value = "$todayReminders${stringResource(R.string.times_unit)}",
                    valueColor = OrangeWarning
                )
            }
        }
    }
}

@Composable
fun StatisticItem(
    icon: ImageVector,
    title: String,
    value: String,
    valueColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = title,
            fontSize = 12.sp,
            color = TextSecondary
        )
        
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UsageStatisticsCard(
    recordsCount: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE8E8FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    tint = Color(0xFF6C5CE7),
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.usage_statistics),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                
                Text(
                    text = stringResource(R.string.view_detailed_charts),
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
            
            Text(
                text = recordsCount.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            Text(
                text = stringResource(R.string.records_unit),
                fontSize = 12.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun StatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckBox,
                contentDescription = null,
                tint = IconGray,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = stringResource(R.string.current_status),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = GreenSuccess
            ) {
                Text(
                    text = "正在守护",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// Preview功能 - 用于在Android Studio中预览界面
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun MainScreenPreview() {
    ThrowItTheme {
        MainScreenContent(
            isMonitoringEnabled = true,
            lastCheckTime = "2分钟前",
            todayReminders = 3,
            recordsCount = 6,
            onToggleMonitoring = {},
            onNavigateToRecords = {},
            onNavigateToSettings = {},
            onNavigateToWhitelist = {}
        )
    }
}
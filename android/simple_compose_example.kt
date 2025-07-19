// Compose 极简理解示例 - 类比后端思维

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 1. 最简单的组件 - 类似后端返回HTML
@Composable
fun HelloWorld() {
    Text("Hello World")  // 就像 return "Hello World"
}

// 2. 带参数的组件 - 类似后端方法
@Composable
fun UserGreeting(name: String) {
    Text("Hello, $name!")  // 就像 return "Hello, " + name
}

// 3. 布局组件 - 类似HTML div
@Composable
fun SimpleCard() {
    Card {  // 就像 <div class="card">
        Column {  // 就像 <div style="flex-direction: column">
            Text("标题")
            Text("内容")
        }
    }
}

// 4. 交互组件 - 类似表单处理
@Composable
fun Counter() {
    // 状态变量 - 类似后端的局部变量
    var count by remember { mutableStateOf(0) }
    
    Column {
        Text("计数: $count")
        Button(
            onClick = { count++ }  // 类似后端的 count++
        ) {
            Text("点击+1")
        }
    }
}

// 5. 列表显示 - 类似后端遍历
@Composable
fun TodoList(todos: List<String>) {
    Column {
        todos.forEach { todo ->  // 就像后端的 for each
            Text("• $todo")
        }
    }
}

// 6. 你的项目中的实际例子
@Composable
fun SimpleMainScreen() {
    Column(
        modifier = Modifier.padding(16.dp)  // 就像CSS的padding
    ) {
        // 标题
        Text("ThrowIt 应用")
        
        Spacer(modifier = Modifier.height(16.dp))  // 就像 <br>
        
        // 状态卡片
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("监控状态: 开启")
                Text("今日提醒: 3次")
                
                Button(onClick = { /* 切换监控 */ }) {
                    Text("切换监控")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 功能按钮
        Row {
            Button(onClick = { /* 打开设置 */ }) {
                Text("设置")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /* 查看记录 */ }) {
                Text("记录")
            }
        }
    }
}

/* 
对比理解：

后端Controller:
@GetMapping("/user")
public String getUser(String name) {
    return "Hello " + name;
}

Compose组件:
@Composable
fun UserGreeting(name: String) {
    Text("Hello $name")
}

都是：接收参数 → 处理逻辑 → 返回结果
只是后端返回字符串，Compose返回UI
*/
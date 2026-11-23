package com.example.roomtareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.TaskViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TaskScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val taskList by viewModel.tasks.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Mis Tareas (Room)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.addTask(title, desc)
                    title = ""
                    desc = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(taskList) { task ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(task.title, style = MaterialTheme.typography.bodyLarge)
                            Text(task.description, style = MaterialTheme.typography.bodyMedium)
                        }
                        TextButton(onClick = { viewModel.deleteTask(task) }) {
                            Text("Borrar")
                        }
                    }
                }
            }
        }
    }
}
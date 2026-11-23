package ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.Task
import data.TaskDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val db = TaskDatabase.getDatabase(application)
    private val dao = db.taskDao()

    val tasks = dao.getAllTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(title: String, desc: String) {
        viewModelScope.launch {
            dao.insertTask(Task(title = title, description = desc))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }
}
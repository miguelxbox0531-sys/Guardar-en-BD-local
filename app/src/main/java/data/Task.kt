package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_tareas")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)
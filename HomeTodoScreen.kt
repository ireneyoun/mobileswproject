package com.example.snowtimerapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeTodoScreen(
    studyItems: List<StudyItem>,
    onUpdateItems: (List<StudyItem>) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(studyItems) { item ->
            TodoItem(
                item = item,
                onAddTodo = { newTodo ->
                    val updatedList = studyItems.map {
                        if (it.title == item.title) {
                            it.copy(todos = (it.todos + newTodo).toMutableList())
                        } else it
                    }
                    onUpdateItems(updatedList)
                }
            )
        }
    }
}

@Composable
fun TodoItem(
    item: StudyItem,
    onAddTodo: (String) -> Unit
) {
    var showInput by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row() {
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 18.sp
                )
                Button(
                    onClick = { showInput = !showInput },
                    contentPadding = PaddingValues(4.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Todo"
                    )
                }
            }

            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "fold"
                )
            }
        }

        if (showInput) {
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("To-do를 추가하세요") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            onAddTodo(text)
                            text = ""
                            showInput = false
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("추가")
                }
            }
        }

        item.todos.forEach { todo ->
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {}
                )
                Text(todo)
            }
        }
    }
}
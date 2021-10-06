package com.eun.mytodokotlin.presentation.list

import com.eun.mytodokotlin.data.entity.ToDoEntity

sealed class ToDoListState {
    object UnInitialized: ToDoListState()

    object Loading: ToDoListState()

    data class Success(
        val toDoList: List<ToDoEntity>
    ): ToDoListState()

    object Error: ToDoListState()
}
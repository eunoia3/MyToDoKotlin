package com.eun.mytodokotlin.presentation.detail

import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.presentation.list.ToDoListState

sealed class ToDoDetailState {
    object UnInitialized: ToDoDetailState()

    object Loading: ToDoDetailState()

    data class Success(
        val toDoItem: ToDoEntity
    ): ToDoDetailState()

    object Delete: ToDoDetailState()
    object Modify: ToDoDetailState()
    object Error: ToDoDetailState()
    object Write: ToDoDetailState()
}
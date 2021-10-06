package com.eun.mytodokotlin.domain.todo

import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.data.repository.ToDoRepository
import com.eun.mytodokotlin.domain.UseCase

internal class InsertToDoItemUseCase(private val toDoRepository: ToDoRepository) : UseCase {
    suspend operator fun invoke(todoItem: ToDoEntity) : Long {
        return toDoRepository.insertToDoItem(todoItem)
    }
}
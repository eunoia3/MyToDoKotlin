package com.eun.mytodokotlin.domain.todo

import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.data.repository.ToDoRepository
import com.eun.mytodokotlin.domain.UseCase

internal class UpdateToDoUseCase(private val toDoRepository: ToDoRepository) : UseCase {
    suspend operator fun invoke(toDoEntity: ToDoEntity): Boolean {
        return toDoRepository.updateToDoItem(toDoEntity)
    }
}
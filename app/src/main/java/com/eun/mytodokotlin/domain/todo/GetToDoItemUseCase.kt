package com.eun.mytodokotlin.domain.todo

import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.data.repository.ToDoRepository
import com.eun.mytodokotlin.domain.UseCase

internal class GetToDoItemUseCase(private val toDoRepository: ToDoRepository) : UseCase {
    suspend operator fun invoke(itemId: Long): ToDoEntity? {
        return toDoRepository.getToDoItem(itemId)
    }
}
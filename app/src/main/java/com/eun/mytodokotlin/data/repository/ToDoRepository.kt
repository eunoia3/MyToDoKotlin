package com.eun.mytodokotlin.data.repository

import com.eun.mytodokotlin.data.entity.ToDoEntity

/*
* 1. insertToDoList
* 2. getToDoList
* 3. updateToDoItem
* */
interface ToDoRepository {

    suspend fun getTodoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun insertToDoItem(toDoItem: ToDoEntity): Long

    suspend fun deleteToDoItem(id: Long): Boolean

}
package com.eun.mytodokotlin.data.repository

import com.eun.mytodokotlin.data.entity.ToDoEntity

class TestToDoRepository : ToDoRepository {

    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getTodoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean {
        val foundToDoEntity = toDoList.find { it.id == toDoItem.id }
        if (foundToDoEntity == null) {
            return false
        } else {
            this.toDoList[toDoList.indexOf(foundToDoEntity)] = toDoItem
            return true
        }

    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        return toDoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        toDoList.clear()
    }

    override suspend fun insertToDoItem(toDoItem: ToDoEntity): Long {
        this.toDoList.add(toDoItem)
        return toDoItem.id
    }

    override suspend fun deleteToDoItem(id: Long): Boolean {
        val foundToDoEntity = toDoList.find { it.id == id }
        return if (foundToDoEntity == null) {
            false
        } else {
            this.toDoList.removeAt(toDoList.indexOf(foundToDoEntity))
            return true
        }
    }


}
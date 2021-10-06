package com.eun.mytodokotlin.data.repository

import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultToDoRepository(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
) : ToDoRepository {

    override suspend fun getTodoList(): List<ToDoEntity> = withContext(ioDispatcher) {
        toDoDao.getAll()
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) = withContext(ioDispatcher) {
        toDoDao.insert(toDoList)
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean  = withContext(ioDispatcher) {
        toDoDao.update(toDoItem)
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity?  = withContext(ioDispatcher) {
        toDoDao.getById(itemId)
    }

    override suspend fun deleteAll()  = withContext(ioDispatcher) {
        toDoDao.deleteAll()
    }

    override suspend fun insertToDoItem(toDoItem: ToDoEntity): Long  = withContext(ioDispatcher) {
        toDoDao.insert(toDoItem)
    }

    override suspend fun deleteToDoItem(id: Long): Boolean  = withContext(ioDispatcher) {
        toDoDao.delete(id)
    }
}
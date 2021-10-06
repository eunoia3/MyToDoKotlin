package com.eun.mytodokotlin.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.domain.todo.DeleteAllToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.GetToDoListUseCase
import com.eun.mytodokotlin.domain.todo.UpdateToDoUseCase
import com.eun.mytodokotlin.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*
* 필요한 UseCase
* 1. GetToDoListUseCase
* 2. UpdateToDoUseCase
* 3. DeleteAllToDoItemUseCase
*
* */
internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase

) : BaseViewModel() {
//    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
//    val todoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData
//
//    fun fetchData(): Job = viewModelScope.launch {
//        _toDoListLiveData.postValue(getToDoListUseCase()!!)
//    }
//
//    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
//        updateToDoUseCase(toDoEntity)
//    }
//
//    fun deleteAll()  = viewModelScope.launch {
//        deleteAllToDoItemUseCase()
//        _toDoListLiveData.postValue(listOf())  //listOf(): 리스트 비우기?
//    }


    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val todoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
        updateToDoUseCase(toDoEntity)
    }

    fun deleteAll()  = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        deleteAllToDoItemUseCase()
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }
}
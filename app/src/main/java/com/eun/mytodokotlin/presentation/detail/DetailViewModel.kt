package com.eun.mytodokotlin.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.domain.todo.*
import com.eun.mytodokotlin.domain.todo.DeleteToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.GetToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.InsertToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.UpdateToDoUseCase
import com.eun.mytodokotlin.presentation.BaseViewModel
import com.eun.mytodokotlin.presentation.list.ToDoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData =
        MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {

        when (detailMode) {
            DetailMode.WRITE -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getToDoItemUseCase(id)?.let {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

    fun deleteTodo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            if (deleteToDoItemUseCase(id)) {
                _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
            } else {
                _toDoDetailLiveData.postValue(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun writeTodo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when (detailMode) {
            DetailMode.WRITE -> {
                try {
                    val toDoEntity = ToDoEntity(
                        id = id,
                        title = title,
                        description = description
                    )
                    id = insertToDoItemUseCase(toDoEntity)
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                    detailMode = DetailMode.DETAIL
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
            DetailMode.DETAIL -> {
                try {
                    getToDoItemUseCase(id)?.let {
                        val updateToDoEntity = it.copy(title = title, description = description)
                        updateToDoUseCase(updateToDoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateToDoEntity))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }

        }
    }
}
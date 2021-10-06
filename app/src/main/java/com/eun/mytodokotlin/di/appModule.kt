package com.eun.mytodokotlin.di

import android.content.Context
import androidx.room.Room
import com.eun.mytodokotlin.data.local.db.ToDoDatabase
import com.eun.mytodokotlin.data.repository.DefaultToDoRepository
import com.eun.mytodokotlin.data.repository.ToDoRepository
import com.eun.mytodokotlin.domain.todo.DeleteAllToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.DeleteToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.GetToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.GetToDoListUseCase
import com.eun.mytodokotlin.domain.todo.InsertToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.InsertToDoListUseCase
import com.eun.mytodokotlin.domain.todo.UpdateToDoUseCase
import com.eun.mytodokotlin.presentation.detail.DetailMode
import com.eun.mytodokotlin.presentation.detail.DetailViewModel
import com.eun.mytodokotlin.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }


    //ViewModel
    viewModel {
        ListViewModel(get(), get(), get())
    }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(
            detailMode = detailMode,
            id = id,
            getToDoItemUseCase = get(),
            deleteToDoItemUseCase = get(),
            updateToDoUseCase = get(),
            insertToDoItemUseCase = get()
        )
    }

    //UseCase
    factory {
        GetToDoListUseCase(get())
    }
    factory {
        InsertToDoListUseCase(get())
    }
    factory {
        UpdateToDoUseCase(get())
    }

    factory {
        GetToDoItemUseCase(get())
    }

    factory {
        DeleteAllToDoItemUseCase(get())
    }
    factory {
        InsertToDoItemUseCase(get())
    }
    factory {
        DeleteToDoItemUseCase(get())
    }

    //Repository
    single<ToDoRepository> {
        DefaultToDoRepository(get(), get())
    }

    single {
        provideDB(androidApplication())
    }
    single {
        provideToDoDao(get())
    }

}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()
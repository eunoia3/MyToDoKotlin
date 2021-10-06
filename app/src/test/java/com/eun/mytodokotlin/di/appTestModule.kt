package com.eun.mytodokotlin.di

import com.eun.mytodokotlin.data.repository.TestToDoRepository
import com.eun.mytodokotlin.data.repository.ToDoRepository
import com.eun.mytodokotlin.domain.todo.*
import com.eun.mytodokotlin.presentation.detail.DetailMode
import com.eun.mytodokotlin.presentation.detail.DetailViewModel
import com.eun.mytodokotlin.presentation.list.ListViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

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
        TestToDoRepository()
    }
}
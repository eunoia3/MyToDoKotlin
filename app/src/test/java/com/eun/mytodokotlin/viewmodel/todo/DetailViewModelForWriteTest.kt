package com.eun.mytodokotlin.viewmodel.todo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.presentation.detail.DetailMode
import com.eun.mytodokotlin.presentation.detail.DetailViewModel
import com.eun.mytodokotlin.presentation.detail.ToDoDetailState
import com.eun.mytodokotlin.presentation.list.ListViewModel
import com.eun.mytodokotlin.presentation.list.ToDoListState
import com.eun.mytodokotlin.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

/*
* [DetailViewModelForWriteTest] 를 테스트하기 위한 Unit Test Class
* 1. test viewModel fetch
* 2. test insert todo
* */
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {

    private val id = 0L

    private val detailViewModel by inject<DetailViewModel> {
        parametersOf(DetailMode.WRITE, id)
    }
    private val listViewModel by inject<ListViewModel>()

    @get:Rule
    var instantExecutorRule =
        InstantTaskExecutorRule()  //java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked.

    private val todo = ToDoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false

    )


    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
//                ToDoDetailState.Loading,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()
        val listTestObservable = listViewModel.todoListLiveData.test()

        detailViewModel.writeTodo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )

        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        //뒤로나가서 리스트 보기
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf(todo))
            )
        )
    }

}
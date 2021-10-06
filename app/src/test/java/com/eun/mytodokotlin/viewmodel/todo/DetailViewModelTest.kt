package com.eun.mytodokotlin.viewmodel.todo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.domain.todo.InsertToDoItemUseCase
import com.eun.mytodokotlin.presentation.detail.DetailMode
import com.eun.mytodokotlin.presentation.detail.DetailViewModel
import com.eun.mytodokotlin.presentation.detail.ToDoDetailState
import com.eun.mytodokotlin.presentation.list.ListViewModel
import com.eun.mytodokotlin.presentation.list.ToDoListState
import com.eun.mytodokotlin.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import java.lang.Exception

/*
* [DetailViewModelTest] 를 테스트하기 위한 Unit Test Class
*
* 1. initData() - 불러 올 수 있는 데이터 설정해주기
* 2. test viewModel fetch - viewModel 이 fetch 함수 호출 했을 때 데이터가 잘 불러와 지는지
* 3. test Item Delete todo
* 4. test Item Update todo
* */
@ExperimentalCoroutinesApi
internal class DetailViewModelTest : ViewModelTest() {

    private val id = 1L

    private val detailViewModel by inject<DetailViewModel> {
        parametersOf(DetailMode.DETAIL, id)
    }
    private val listViewModel by inject<ListViewModel>()
    private val insertToDoItemUseCase: InsertToDoItemUseCase by inject()

    @get:Rule
    var instantExecutorRule =
        InstantTaskExecutorRule()  //java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked.

    private val todo = ToDoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false

    )


    @Before
    fun init() {
        initData()
    }


    private fun initData() = runBlockingTest {
        insertToDoItemUseCase(todo)
    }

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()

        detailViewModel.deleteTodo()
        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )
        val listTestObservable = listViewModel.todoListLiveData.test()
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }


    @Test
    fun `test update todo`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        val updateTitle = "title 1 update"
        val updateDescription = "Description"
        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )
        detailViewModel.writeTodo(
            title = updateTitle,
            description = updateDescription
        )
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateToDo)
            )
        )
    }
}
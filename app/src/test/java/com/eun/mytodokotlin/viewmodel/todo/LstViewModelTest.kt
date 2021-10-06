package com.eun.mytodokotlin.viewmodel.todo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eun.mytodokotlin.data.entity.ToDoEntity
import com.eun.mytodokotlin.domain.todo.GetToDoItemUseCase
import com.eun.mytodokotlin.domain.todo.InsertToDoListUseCase
import com.eun.mytodokotlin.presentation.list.ListViewModel
import com.eun.mytodokotlin.presentation.list.ToDoListState
import com.eun.mytodokotlin.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.inject

/*
* [LstViewModelTest] 를 테스트하기 위한 Unit Test Class
*
* 1. initData() - 설정할 수 있는 데이터 설정해주기
* 2. test viewModel fetch - viewModel 이 fetch 함수 호출 했을 때 데이터가 잘 불러와 지는지
* 3. test Item Update
* 4. test Item Delete All
*
* */
@ExperimentalCoroutinesApi
internal class LstViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()
    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false

        )
    }

    /*
    * 필요한 Usecase 들
    * 1. InsertToDoListUseCase
    * 2. GetToDoItemUseCase
    * */

    @Before
    fun init() {
        initData()
    }
/*
    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test: 입력된 데이터를 불러와서 검증한다
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )

    }

    //Test: 데이터를 업데이트 했을 떄 잘 반영되는가
    @Test
    fun `test Item Update`(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted?: false ==  todo.hasCompleted)
    }


    //Test: 데이터를 다 날렸을 떄 빈 상태로 보여지는가
    @Test
    fun `test Item Delete All`(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
//        viewModel.fetchData()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
//                mockList,
                listOf())
            )
    }

 */

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test: 입력된 데이터를 불러와서 검증한다
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(mockList)
            )

        )
    }


    //Test: 데이터를 업데이트 했을 떄 잘 반영되는가
    @Test
    fun `test Item Update`(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted)
    }


    //Test: 데이터를 다 날렸을 떄 빈 상태로 보여지는가
    @Test
    fun `test Item Delete All`(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
//        viewModel.fetchData()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }
}


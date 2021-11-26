package project.xinyuan.sales.view.todolist

import project.xinyuan.sales.model.sales.master.DataTodo

interface TodoListContract {

    fun messageGetTodoList(msg:String)
    fun getDataTodoList(data:List<DataTodo?>?)

}
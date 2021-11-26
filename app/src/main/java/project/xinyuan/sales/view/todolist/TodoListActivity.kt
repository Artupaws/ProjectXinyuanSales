package project.xinyuan.sales.view.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterTodoList
import project.xinyuan.sales.databinding.ActivityTodoListBinding
import project.xinyuan.sales.model.sales.master.DataTodo

class TodoListActivity : AppCompatActivity(), TodoListContract {

    private lateinit var binding: ActivityTodoListBinding
    private lateinit var presenter: TodoListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = TodoListPresenter(this, this)
        presenter.getTodoList()
        refresh()

        binding.toolbarTodoList.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTodoList.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarTodoList.title = "Todo List"

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getTodoList()
        }
    }

    override fun messageGetTodoList(msg: String) {
        Log.d("getTodoList", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataTodoList(data: List<DataTodo?>?) {
        binding.rvTodoList.apply {
            adapter = AdapterTodoList(applicationContext, data)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }
}
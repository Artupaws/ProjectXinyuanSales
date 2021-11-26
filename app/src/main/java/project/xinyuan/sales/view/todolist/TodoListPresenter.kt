package project.xinyuan.sales.view.todolist

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.sales.ResponseGetTodoList
import retrofit2.Call
import retrofit2.Response

class TodoListPresenter(val view: TodoListContract, val context: Context) {

    fun getTodoList(){
        val getTodoList = NetworkConfig().getConnectionXinyuanBearer(context).getTodoList()
        getTodoList.enqueue(object : retrofit2.Callback<ResponseGetTodoList>{
            override fun onResponse(call: Call<ResponseGetTodoList>, response: Response<ResponseGetTodoList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDataTodoList(data)
                    view.messageGetTodoList(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetTodoList(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetTodoList>, t: Throwable) {
                view.messageGetTodoList(t.localizedMessage.toString())
            }

        })
    }

}
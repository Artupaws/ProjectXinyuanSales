package project.xinyuan.sales.model.sales

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.sales.master.DataTodo

data class ResponseGetTodoList(

    @field:SerializedName("data")
	val data: List<DataTodo?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetTodoList(

    @field:SerializedName("data")
	val data: List<DataTodo?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
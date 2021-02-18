package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class DataTodo(

	@field:SerializedName("id_sales")
	val idSales: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("done_date")
	val doneDate: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("done")
	val done: Int? = null
)
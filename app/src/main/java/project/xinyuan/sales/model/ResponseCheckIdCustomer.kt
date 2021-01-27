package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseCheckIdCustomer(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
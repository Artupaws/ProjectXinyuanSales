package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetListCustomer(

	@field:SerializedName("data")
	val data: DataCustomer? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseMakePayment(

	@field:SerializedName("data")
	val dataPayment: DataPayment? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
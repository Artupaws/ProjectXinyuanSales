package project.xinyuan.sales.model.customer

import com.google.gson.annotations.SerializedName

data class ResponseUploadImageCustomer(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
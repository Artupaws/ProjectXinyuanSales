package project.xinyuan.sales.model.customer

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.customer.master.DataCustomer

data class ResponseGetListCustomer(

	@field:SerializedName("data")
	val data: List<DataCustomer?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
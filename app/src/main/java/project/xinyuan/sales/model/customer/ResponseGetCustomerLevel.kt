package project.xinyuan.sales.model.customer

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.customer.master.CustomerLevel

data class ResponseGetCustomerLevel(

    @field:SerializedName("data")
	val data: MutableList<CustomerLevel?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
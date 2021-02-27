package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetCustomerLevel(

        @field:SerializedName("data")
	val data: MutableList<CustomerLevel?>? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
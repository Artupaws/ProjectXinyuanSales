package project.xinyuan.sales.model.customer

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.customer.master.DataCustomer

data class ResponseRegisterDataCustomer(

    @field:SerializedName("data")
	val dataCustomer: DataCustomer? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
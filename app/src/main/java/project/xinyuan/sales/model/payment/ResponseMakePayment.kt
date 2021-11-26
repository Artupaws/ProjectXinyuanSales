package project.xinyuan.sales.model.payment

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.payment.master.DataPayment

data class ResponseMakePayment(

    @field:SerializedName("data")
	val dataPayment: DataPayment? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
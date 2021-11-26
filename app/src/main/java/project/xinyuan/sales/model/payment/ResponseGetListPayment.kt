package project.xinyuan.sales.model.payment

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.payment.master.DataPayment

data class ResponseGetListPayment(

    @field:SerializedName("data")
	val data: List<DataPayment?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
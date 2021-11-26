package project.xinyuan.sales.model.paymentaccount

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount

data class ResponseGetPaymentAccounts(

    @field:SerializedName("data")
	val data: List<DataPaymentAccount?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
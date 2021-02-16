package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetPaymentAccounts(

        @field:SerializedName("data")
	val data: List<DataPaymentAccount?>? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
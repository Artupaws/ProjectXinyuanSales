package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetListPayment(

        @field:SerializedName("data")
	val data: List<DataPayment?>? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
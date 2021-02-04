package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetTransactionDetail(

    @field:SerializedName("data")
	val dataTransaction: List<DataTransaction?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
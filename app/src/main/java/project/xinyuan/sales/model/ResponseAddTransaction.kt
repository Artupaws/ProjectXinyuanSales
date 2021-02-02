package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseAddTransaction(

        @field:SerializedName("data")
	val dataFormalTransaction: DataFormalTransaction? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
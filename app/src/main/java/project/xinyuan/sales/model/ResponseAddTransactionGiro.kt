package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseAddTransactionGiro(

        @field:SerializedName("data")
	val dataAddTransactionGiro: DataAddTransactionGiro? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
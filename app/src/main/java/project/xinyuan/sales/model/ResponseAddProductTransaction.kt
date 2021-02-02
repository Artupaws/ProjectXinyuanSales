package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseAddProductTransaction(

        @field:SerializedName("data")
	val dataAddProductTransaction: DataAddProductTransaction? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
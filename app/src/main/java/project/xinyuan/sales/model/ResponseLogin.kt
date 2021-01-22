package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

        @field:SerializedName("data")
	val dataLogin: DataLogin? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
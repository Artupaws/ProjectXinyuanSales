package project.xinyuan.sales.model.sales

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.sales.master.DataLogin

data class ResponseLogin(

    @field:SerializedName("data")
	val dataLogin: DataLogin? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
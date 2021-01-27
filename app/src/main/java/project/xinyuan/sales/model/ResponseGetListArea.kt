package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetListArea(

        @field:SerializedName("data")
	val data: List<DataArea?>? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
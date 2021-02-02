package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseGetDetailSales(

        @field:SerializedName("data")
	val dataSales: DataSales? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)
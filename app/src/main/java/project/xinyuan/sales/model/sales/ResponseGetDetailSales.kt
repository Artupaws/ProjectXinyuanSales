package project.xinyuan.sales.model.sales

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.sales.master.DataSales

data class ResponseGetDetailSales(

    @field:SerializedName("data")
	val dataSales: DataSales? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
package project.xinyuan.sales.model.sales

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.sales.master.DataProfileSales

data class ResponseUpdateProfileSales(

	@field:SerializedName("data")
	val dataProfileSales: DataProfileSales? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
package project.xinyuan.sales.model.bank

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.bank.master.DataBank

data class ResponseGetListBank(

    @field:SerializedName("data")
	val data: MutableList<DataBank?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
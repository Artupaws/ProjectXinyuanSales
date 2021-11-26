package project.xinyuan.sales.model.transaction

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.transaction.master.DataTransaction

data class ResponseGetTransactionDetail(

    @field:SerializedName("data")
	val dataTransaction: List<DataTransaction?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
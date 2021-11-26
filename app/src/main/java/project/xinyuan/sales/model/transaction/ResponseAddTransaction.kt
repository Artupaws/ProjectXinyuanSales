package project.xinyuan.sales.model.transaction

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.transaction.master.DataFormalTransaction

data class ResponseAddTransaction(

    @field:SerializedName("data")
	val dataFormalTransaction: DataFormalTransaction? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
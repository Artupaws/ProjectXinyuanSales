package project.xinyuan.sales.model.giro

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.giro.DataAddTransactionGiro

data class ResponseAddTransactionGiro(

    @field:SerializedName("data")
	val dataAddTransactionGiro: DataAddTransactionGiro? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
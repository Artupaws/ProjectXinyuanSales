package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class DataPaymentAccount(

	@field:SerializedName("account_number")
	val accountNumber: String? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("account_name")
	val accountName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
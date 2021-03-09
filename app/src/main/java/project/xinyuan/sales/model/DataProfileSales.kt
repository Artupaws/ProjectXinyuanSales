package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class DataProfileSales(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
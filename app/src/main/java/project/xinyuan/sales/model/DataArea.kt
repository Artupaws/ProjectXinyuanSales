package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class DataArea(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
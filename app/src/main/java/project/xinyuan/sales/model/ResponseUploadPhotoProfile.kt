package project.xinyuan.sales.model

import com.google.gson.annotations.SerializedName

data class ResponseUploadPhotoProfile(

		@field:SerializedName("data")
	val dataProfileSales: DataProfileSales? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)
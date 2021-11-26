package project.xinyuan.sales.model.area

import com.google.gson.annotations.SerializedName
import project.xinyuan.sales.model.area.master.DataArea

data class ResponseGetListArea(

    @field:SerializedName("data")
	val data: List<DataArea?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)
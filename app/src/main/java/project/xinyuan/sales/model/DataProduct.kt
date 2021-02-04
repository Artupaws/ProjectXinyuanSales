package project.xinyuan.sales.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataProduct(

	@field:SerializedName("factory")
	val factory: String? = null,

	@field:SerializedName("colour")
	val colour: Any? = null,

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("weight")
	val weight: Any? = null,

	@field:SerializedName("logo")
	val logo: Any? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("gross_weight")
	val grossWeight: Any? = null
):Parcelable
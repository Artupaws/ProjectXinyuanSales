package project.xinyuan.sales.model.sales.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataSales(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("id_area")
	val idArea: Int? = null,
):Parcelable
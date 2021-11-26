package project.xinyuan.sales.model.company

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import project.xinyuan.sales.model.company.master.CompanyItem

@Parcelize
data class ResponseGetCompanyList(

	@field:SerializedName("data")
	val data: List<CompanyItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable


package project.xinyuan.sales.model.customer.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCustomer(

	@field:SerializedName("company_phone")
	val companyPhone: String? = null,

	@field:SerializedName("administrator_id")
	val administratorId: String? = null,

	@field:SerializedName("administrator_npwp")
	val administratorNpwp: String? = null,

	@field:SerializedName("company_npwp")
	val companyNpwp: String? = null,

	@field:SerializedName("administrator_phone")
	val administratorPhone: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_area")
	val idArea: Int? = null,

	@field:SerializedName("invited_by")
	val invitedBy: Int? = null,

	@field:SerializedName("company_address")
	val companyAddress: String? = null,

	@field:SerializedName("administrator_birthdate")
	val administratorBirthdate: String? = null,

	@field:SerializedName("administrator_address")
	val administratorAddress: String? = null,

	@field:SerializedName("administrator_name")
	val administratorName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("tempo")
	val tempo: Int? = null,

	@field:SerializedName("loan_limit")
	val loanLimit: Long? = null,

	@field:SerializedName("id_level")
	val idLevel: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl:String? = null
):Parcelable
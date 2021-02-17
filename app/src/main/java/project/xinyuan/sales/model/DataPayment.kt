package project.xinyuan.sales.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPayment(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("paid")
	val paid: String? = null,

	@field:SerializedName("paymentaccount")
	val paymentAccount: DataPaymentAccount? = null,

	@field:SerializedName("id")
	val id: Int? = null

):Parcelable
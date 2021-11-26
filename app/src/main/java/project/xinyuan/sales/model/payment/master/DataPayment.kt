package project.xinyuan.sales.model.payment.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount

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
package project.xinyuan.sales.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Field

@Parcelize
data class DataGiro (

    @field:SerializedName("id")
    val id:Int? = null,

    @field:SerializedName("id_bank")
    val idBank:Int? = null,

    @field:SerializedName("bank")
    val bankName:String? = null,

    @field:SerializedName("id_customer")
    val idCustomer:Int? = null,

    @field:SerializedName("id_transaction")
    val idTransaction:Int? = null,

    @field:SerializedName("giro_number")
    val giroNumber:Int? = null,

    @field:SerializedName("balance")
    val balance:Long? = null,

    @field:SerializedName("date_received")
    val dateReceived:String? = null,

    @field:SerializedName("cashed")
    val status:Int? = null,

    @field:SerializedName("id_payment_account")
    val idPaymentAccount:Int? = null
):Parcelable
package project.xinyuan.sales.model.transaction.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import project.xinyuan.sales.model.giro.master.DataGiro
import project.xinyuan.sales.model.payment.master.DataPayment

@Parcelize
data class DataTransaction(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("paid")
	val paid: Int? = null,

	@field:SerializedName("transactiondetails")
	val transactiondetails: List<TransactiondetailsItem?>? = null,

	@field:SerializedName("transactionpayment")
	val transactionpayment: List<DataPayment?>? = null,

	@field:SerializedName("giro")
	val giropayment: List<DataGiro?>? = null,

	@field:SerializedName("payment")
	val payment: String? = null,

	@field:SerializedName("payment_period")
	val paymentPeriod:Int? = null,

	@field:SerializedName("id")
	val idTransaction: Int? = null,

	@field:SerializedName("invoice_number")
	val invoiceNumber: String? = null,

	@field:SerializedName("debt")
	val debt: Int? = null,

	@field:SerializedName("sales")
	val sales: String? = null,

	@field:SerializedName("total_payment")
	val totalPayment: Int? = null,

	@field:SerializedName("customer")
	val customer: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("payment_deadline")
	val paymentDeadline: String? = null
):Parcelable
package project.xinyuan.sales.model.transaction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestCreateTransaction(

	@field:SerializedName("invoice_number")
	val invoiceNumber: String? = null,

	@field:SerializedName("id_customer")
	val idCustomer: Int? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("id_warehouse")
	val idWarehouse: Int? = null,

	@field:SerializedName("id_company")
	val idCompany: Int? = null,

	@field:SerializedName("payment_period")
	val paymentPeriod: Int? = null,

	@field:SerializedName("payment")
	val payment: String? = null,

	@field:SerializedName("total_payment")
	val totalPayment: Int? = null,

	@field:SerializedName("product")
	val product: List<ProductItem?>? = null,

	@field:SerializedName("id_payment_account")
	val idPaymentAccount: Int? = null,

) : Parcelable

@Parcelize
data class ProductItem(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("id_product")
	val idProduct: Int? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null
) : Parcelable

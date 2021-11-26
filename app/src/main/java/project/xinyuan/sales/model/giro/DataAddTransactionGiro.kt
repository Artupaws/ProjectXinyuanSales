package project.xinyuan.sales.model.giro

import com.google.gson.annotations.SerializedName

data class DataAddTransactionGiro(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("id_sales")
	val idSales: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_customer")
	val idCustomer: String? = null,

	@field:SerializedName("paid")
	val paid: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("payment")
	val payment: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("invoice_number")
	val invoiceNumber: String? = null,

	@field:SerializedName("total_payment")
	val totalPayment: String? = null,

	@field:SerializedName("payment_deadline")
	val paymentDeadline: String? = null
)
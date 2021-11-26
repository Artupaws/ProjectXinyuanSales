package project.xinyuan.sales.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionProduct(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("id_product")
	val idProduct: Int? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id_transaction")
	val idTransaction: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null
)
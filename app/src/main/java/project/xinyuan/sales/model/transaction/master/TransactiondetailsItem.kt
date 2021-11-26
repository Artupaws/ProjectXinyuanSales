package project.xinyuan.sales.model.transaction.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import project.xinyuan.sales.model.product.master.DataProduct

@Parcelize
data class TransactiondetailsItem(

    @field:SerializedName("product")
	val dataProduct: DataProduct? = null,

    @field:SerializedName("subtotal")
	val subTotal: Int? = null,

    @field:SerializedName("quantity")
	val quantity: Int? = null,

    @field:SerializedName("price")
	val price: Int? = null
):Parcelable
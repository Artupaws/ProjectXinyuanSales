package project.xinyuan.sales.model.warehouse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import project.xinyuan.sales.model.warehouse.master.DataWarehouse

@Parcelize
data class ResponseGetWarehouseList(

	@field:SerializedName("data")
	val data: List<DataWarehouse?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable


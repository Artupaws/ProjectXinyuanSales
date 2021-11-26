package project.xinyuan.sales.model.warehouse.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataWarehouse(

    @field:SerializedName("area")
    val area: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("capacity")
    val capacity: Int? = null
) : Parcelable
package project.xinyuan.sales.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "carts")
@Parcelize
data class CartItem (
        @PrimaryKey(autoGenerate = true)
        var id:Int=0,

        @ColumnInfo(name = "type")
        var type: String="",

        @ColumnInfo(name = "photo")
        var photo: String="",

        @ColumnInfo(name = "price")
        var price: String="",

        @ColumnInfo(name = "subTotal")
        var subTotal: String="",

        @ColumnInfo(name = "total")
        var total: String=""
):Parcelable

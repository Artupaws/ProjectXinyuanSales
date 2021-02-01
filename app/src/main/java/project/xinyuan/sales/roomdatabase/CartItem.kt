package project.xinyuan.sales.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Cart")
data class CartItem(

        @PrimaryKey
        val id: Int,
        @ColumnInfo
        val type: String,
        @ColumnInfo
        val photo: String,
        @ColumnInfo
        val price: String,
        @ColumnInfo
        val total: Int
)

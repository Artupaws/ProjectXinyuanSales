package project.xinyuan.sales.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {

    @Insert
    fun addToCart(cartItem: CartItem)

    @Query("SELECT * FROM Cart")
    fun getData(): MutableList<CartItem?>?

    @Query("SELECT EXISTS (SELECT 1 FROM cart WHERE id=:id)")
    fun isAddToCart(id: Int): Int

    @Query("select COUNT (*) from Cart")
    fun countCart(): Int

    @Query("DELETE FROM Cart WHERE id=:id ")
    fun deleteItem(id: Int): Int
}
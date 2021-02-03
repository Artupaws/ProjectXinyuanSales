package project.xinyuan.sales.roomdatabase

import androidx.room.*

@Dao
interface CartDao {

    @Insert
    fun insert(cart: CartItem)

    @Update
    fun update(cart: CartItem)

    @Delete
    fun delete(cart: CartItem)

    @Query("SELECT * FROM carts")
    fun getAll() : List<CartItem>

    @Query("SELECT * FROM carts WHERE id = :id")
    fun getById(id: Int) : List<CartItem>

    @Query("DELETE FROM carts")
    fun deleteAll()
}
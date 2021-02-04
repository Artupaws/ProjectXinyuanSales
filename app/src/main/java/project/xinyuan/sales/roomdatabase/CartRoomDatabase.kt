package project.xinyuan.sales.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 2, exportSchema = false)
abstract class CartRoomDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: CartRoomDatabase? = null

        fun getDatabase(context: Context):CartRoomDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, CartRoomDatabase::class.java,
                    "cart_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }

    abstract fun getCartDao() : CartDao

}
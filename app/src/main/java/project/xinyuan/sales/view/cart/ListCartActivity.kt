package project.xinyuan.sales.view.cart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListCart
import project.xinyuan.sales.databinding.ActivityListCartBinding
import project.xinyuan.sales.roomdatabase.CartItem
import project.xinyuan.sales.roomdatabase.CartRoomDatabase
import project.xinyuan.sales.view.dashboard.DashboardActivity

class ListCartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListCartBinding
    var carts: List<CartItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getListCartData()
        binding.toolbarListCart.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarListCart.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbarListCart.title = "Customer Order"

    }

    private fun getListCartData(){
        val database = CartRoomDatabase.getDatabase(applicationContext)
        val dao = database.getCartDao()
        val listItems = arrayListOf<CartItem>()
        listItems.addAll(dao.getAll())
        setupListCart(listItems)
    }

    private fun setupListCart(listItem:ArrayList<CartItem>){
        binding.rvCartOrder.apply {
            layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL, false)
            adapter = AdapterListCart(applicationContext, listItem)
        }
    }
}
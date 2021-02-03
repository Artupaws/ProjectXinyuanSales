package project.xinyuan.sales.view.addfragment.addordercustomer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductAddOrder
import project.xinyuan.sales.databinding.ActivityAddOrderCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.model.DataProduct
import project.xinyuan.sales.roomdatabase.CartItem
import project.xinyuan.sales.roomdatabase.CartRoomDatabase
import project.xinyuan.sales.view.cart.ListCartActivity

class AddOrderCustomerActivity : AppCompatActivity(), View.OnClickListener, AddOrderCustomerContract {

    private lateinit var binding: ActivityAddOrderCustomerBinding
    private lateinit var presenter: AddOrderCustomerPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var statusAdd:Int = 0
    private var statusRemove:Int = 0
    private var listCart:ArrayList<CartItem>? = null
    private lateinit var detailCustomer:DataCustomer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrderCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = AddOrderCustomerPresenter(this, this)
        presenter.getListProduct()

        refresh()
        getListCart()
        binding.toolbarAddOrderCustomer.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddOrderCustomer.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddOrderCustomer.title = "Add Order Customer"
        binding.btnPlaceOrder.setOnClickListener(this)

        detailCustomer = intent.getParcelableExtra("detailCustomer")!!
        binding.tvCompanyName.text = detailCustomer.companyName
        binding.tvAdminName.text = detailCustomer.administratorName
        binding.tvAdminPhone.text = detailCustomer.administratorPhone

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_place_order ->{
                val intent = Intent(applicationContext, ListCartActivity::class.java)
                intent.putExtra("detailCustomer", detailCustomer)
                startActivity(intent)
            }
        }
    }

    private fun getListCart(){
        val database = CartRoomDatabase.getDatabase(applicationContext)
        val dao = database.getCartDao()
        val listItemCart = arrayListOf<CartItem>()
        listItemCart.addAll(dao.getAll())
        listCart = listItemCart
        binding.btnPlaceOrder.isEnabled = listItemCart.isNotEmpty()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("check"))
    }

    private val mMessageReceiver : BroadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action){
                "check"-> {
                    val add = intent.getIntExtra("addProduct", 0)
                    val remove = intent.getIntExtra("removeProduct", 0)
                    Log.d("removeProduct", remove.toString())
                    statusAdd = add
                    statusRemove = remove
                    if (statusAdd == 1){
                        getListCart()
                    }
                    if (statusRemove == 1){
                        getListCart()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            if (listCart?.isNotEmpty()!!){
                binding.swipeRefresh.isRefreshing = false
            } else {
                presenter.getListProduct()
            }
        }
    }

    override fun onBackPressed() {
        if (!binding.btnPlaceOrder.isEnabled){
            super.onBackPressed()
        } else {
            Toast.makeText(applicationContext, "Finish the order or delete all order", Toast.LENGTH_LONG).show()
        }
    }

    override fun messageGetListProduct(msg: String) {
        Log.d("getListProduct", msg)
        binding.swipeRefresh.isRefreshing = false
        statusAdd = 0
    }

    override fun getListProduct(data: List<DataProduct?>?) {
        val adapterAddOrder = AdapterListProductAddOrder(this, data)
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterAddOrder
        }
    }
}
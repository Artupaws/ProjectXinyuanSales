package project.xinyuan.sales.view.addfragment.addordercustomer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductAddOrder
import project.xinyuan.sales.databinding.ActivityAddOrderCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.model.DataProduct
import project.xinyuan.sales.view.cart.ListCartActivity

class AddOrderCustomerActivity : AppCompatActivity(), View.OnClickListener, AddOrderCustomerContract {

    private lateinit var binding: ActivityAddOrderCustomerBinding
    private lateinit var presenter: AddOrderCustomerPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var totalOrder:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrderCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = AddOrderCustomerPresenter(this, this)
        presenter.getListProduct()

        refresh()
        setStatePlaceOrder()
        binding.toolbarAddOrderCustomer.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddOrderCustomer.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddOrderCustomer.title = "Add Order Customer"
        binding.btnPlaceOrder.setOnClickListener(this)

        val detailCustomer = intent.getParcelableExtra<DataCustomer>("detailCustomer")
        binding.tvCompanyName.text = detailCustomer.companyName
        binding.tvAdminName.text = detailCustomer.administratorName
        binding.tvAdminPhone.text = detailCustomer.administratorPhone

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_place_order ->{
                val intent = Intent(applicationContext, ListCartActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("total"))
    }

    private val mMessageReceiver : BroadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action){
                "total"-> {
                    val total = intent.getIntExtra("totalOrder", 0)
                    totalOrder = total
                    setStatePlaceOrder()
                    Log.d("totalOrder", total.toString())
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
            presenter.getListProduct()
        }
    }

    private fun setStatePlaceOrder(){
        binding.btnPlaceOrder.isEnabled = totalOrder != 0
    }

    override fun messageGetListProduct(msg: String) {
        Log.d("getListProduct", msg)
        binding.swipeRefresh.isRefreshing = false
        totalOrder = 0
        setStatePlaceOrder()
    }

    override fun getListProduct(data: List<DataProduct?>?) {
        val adapterAddOrder = AdapterListProductAddOrder(this, data)
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterAddOrder
        }
    }
}
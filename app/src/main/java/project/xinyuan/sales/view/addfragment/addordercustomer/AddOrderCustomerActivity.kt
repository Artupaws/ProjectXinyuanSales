package project.xinyuan.sales.view.addfragment.addordercustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductAddOrder
import project.xinyuan.sales.databinding.ActivityAddOrderCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.model.DataProduct

class AddOrderCustomerActivity : AppCompatActivity(), View.OnClickListener, AddOrderCustomerContract {

    private lateinit var binding: ActivityAddOrderCustomerBinding
    private lateinit var presenter: AddOrderCustomerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrderCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = AddOrderCustomerPresenter(this, this)
        presenter.getListProduct()

        refresh()
        binding.toolbarAddOrderCustomer.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddOrderCustomer.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddOrderCustomer.title = "Add Order Customer"

        val detailCustomer = intent.getParcelableExtra<DataCustomer>("detailCustomer")
        binding.tvCompanyName.text = detailCustomer.companyName
        binding.tvAdminName.text = detailCustomer.administratorName
        binding.tvAdminPhone.text = detailCustomer.administratorPhone

    }

    override fun onClick(p0: View?) {
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListProduct()
        }
    }

    override fun messageGetListProduct(msg: String) {
        Log.d("getListProduct", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getListProduct(data: List<DataProduct?>?) {
        val adapterAddOrder = AdapterListProductAddOrder(this, data)
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterAddOrder
        }
    }
}
package project.xinyuan.sales.view.addfragment.choosecustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterChooseCustomer
import project.xinyuan.sales.databinding.ActivityChooseCustomerBinding
import project.xinyuan.sales.model.DataCustomer

class ChooseCustomerActivity : AppCompatActivity(), ChooseCustomerContract {

    private lateinit var binding: ActivityChooseCustomerBinding
    private lateinit var presenter: ChooseCustomerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ChooseCustomerPresenter(this, this)

        binding.toolbarChooseCustomer.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarChooseCustomer.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarChooseCustomer.title = "Choose Customer"
        presenter.getListCustomer()
        refresh()
        searchCustomer()


    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListCustomer()
        }
    }

    private fun searchCustomer(){
        binding.svCustomer.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                (binding.rvCustomer.adapter as AdapterChooseCustomer).filter.filter(p0)
                (binding.rvCustomer.adapter as AdapterChooseCustomer).notifyDataSetChanged()
                return true
            }

        })
    }

    override fun messageGetListCustomer(msg: String) {
        Log.d("getListCustomer", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getListCustomer(data: List<DataCustomer?>?) {
        val adapterChooseCustomer = AdapterChooseCustomer(this, data)
        binding.rvCustomer.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterChooseCustomer
        }
    }
}
package project.xinyuan.sales.view.addfragment.choosecustomer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterChooseCustomer
import project.xinyuan.sales.databinding.ActivityChooseCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.view.addfragment.addcustomerdata.DataCustomerActivty

class ChooseCustomerActivity : AppCompatActivity(), ChooseCustomerContract, View.OnClickListener {

    private lateinit var binding: ActivityChooseCustomerBinding
    private lateinit var presenter: ChooseCustomerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ChooseCustomerPresenter(this, this)
        binding.includeEmpty.relativeActionAdd.setOnClickListener(this)

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
                if (p0?.isNotEmpty()!!){
                    (binding.rvCustomer.adapter as? AdapterChooseCustomer)?.filter?.filter(p0)
                    (binding.rvCustomer.adapter as? AdapterChooseCustomer)?.notifyDataSetChanged()
                } else {
                    (binding.rvCustomer.adapter as? AdapterChooseCustomer)?.filter?.filter("")
                    (binding.rvCustomer.adapter as? AdapterChooseCustomer)?.notifyDataSetChanged()
                }
                return true
            }

        })
    }

    private fun setEmptyLayout(msg:String){
        Log.d("listCustomer", msg)
        if (!msg.contains("Get Customer Success!")){
            binding.rvCustomer.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.includeEmpty.tvEmpty.text = getString(R.string.empty_customer)
            binding.includeEmpty.tvTitleButton.text = getString(R.string.add_customer_button)
        } else {
            binding.rvCustomer.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.GONE
        }
    }

    override fun messageGetListCustomer(msg: String) {
        setEmptyLayout(msg)
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

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.relative_action_add -> {
                val intent = Intent(applicationContext, DataCustomerActivty::class.java)
                startActivity(intent)
            }
        }
    }
}
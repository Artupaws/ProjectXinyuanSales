package project.xinyuan.sales.view.home.customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListCustomer
import project.xinyuan.sales.databinding.FragmentCustomerBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.customer.master.DataCustomer
import project.xinyuan.sales.view.addfragment.addcustomerdata.DataCustomerActivty
import project.xinyuan.sales.view.login.LoginActivity

class CustomerFragment : Fragment(), CustomerContract, View.OnClickListener {

    private var _binding:FragmentCustomerBinding? =null
    private val binding get() = _binding
    private lateinit var presenter: CustomerPresenter
    private lateinit var sharedPref: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferencesHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.includeEmpty?.relativeActionAdd?.setOnClickListener(this)
        presenter = CustomerPresenter(this, requireContext())
        presenter.getListCustomer()
        refresh()
        searchCustomer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun tryLogin(){
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        ActivityCompat.finishAffinity(activity!!)
    }

    private fun searchCustomer(){
        binding?.svCustomer?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (binding?.rvCustomer?.adapter as? AdapterListCustomer)?.filter?.filter(p0)
                    (binding?.rvCustomer?.adapter as? AdapterListCustomer)?.notifyDataSetChanged()
                } else {
                    (binding?.rvCustomer?.adapter as? AdapterListCustomer)?.filter?.filter("")
                    (binding?.rvCustomer?.adapter as? AdapterListCustomer)?.notifyDataSetChanged()
                }
                return true
            }

        })
    }

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getListCustomer()
        }
    }

    private fun setEmptyLayout(msg:String){
        Log.d("listCustomer", msg)
        if (!msg.contains("Get Customer Success!")){
            binding?.rvCustomer?.visibility = View.GONE
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = requireContext().getString(R.string.empty_customer)
            binding?.includeEmpty?.tvTitleButton?.text = requireContext().getString(R.string.add_customer_button)
        } else {
            binding?.rvCustomer?.visibility = View.VISIBLE
            binding?.includeEmpty?.linearEmpty?.visibility = View.GONE
        }
    }

    override fun messageGetListCustomer(msg: String) {
        Log.d("listCustomer", msg)
        setEmptyLayout(msg)
        binding?.swipeRefresh?.isRefreshing = false
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(requireContext(), "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun getListCustomer(data: List<DataCustomer?>?) {
        binding?.rvCustomer?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterListCustomer(requireContext(), data)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.relative_action_add -> {
                val intent = Intent(requireContext(), DataCustomerActivty::class.java)
                startActivity(intent)
            }
        }
    }

}
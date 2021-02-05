package project.xinyuan.sales.view.home.customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListCustomer
import project.xinyuan.sales.databinding.FragmentCustomerBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.view.login.LoginActivity

class CustomerFragment : Fragment(), CustomerContract {

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
        presenter = CustomerPresenter(this, requireContext())
        presenter.getListCustomer()
        refresh()
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

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getListCustomer()
        }
    }

    override fun messageGetListCustomer(msg: String) {
        Log.d("listCustomer", msg)
        binding?.swipeRefresh?.isRefreshing = false
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(requireContext(), "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun getListCustomer(data: List<DataCustomer?>?) {
        val adapterCustomer = AdapterListCustomer(requireContext(), data)
        binding?.rvCustomer?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterCustomer
        }
    }

}
package project.xinyuan.sales.view.home.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.recyclerview.widget.GridLayoutManager
import project.xinyuan.sales.adapter.AdapterListProduct
import project.xinyuan.sales.databinding.FragmentProductBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataProduct
import project.xinyuan.sales.view.login.LoginActivity

class ProductFragment : Fragment(), ProductContract {

    private var _binding:FragmentProductBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: ProductPresenter
    private lateinit var sharedPref:SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater,container, false)
        presenter = ProductPresenter(this, requireContext())
        sharedPref = SharedPreferencesHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getListProduct()
        refresh()
        searchProduct()

    }

    private fun tryLogin(){
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        finishAffinity(activity!!)
    }

    private fun searchProduct(){
        binding?.svProduct?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (binding?.rvProduct?.adapter as AdapterListProduct).filter.filter(p0)
                    (binding?.rvProduct?.adapter as AdapterListProduct).notifyDataSetChanged()
                }
                return true
            }

        })
    }

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getListProduct()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun messageGetListProduct(msg: String) {
        Log.d("listProduct", msg)
        binding?.swipeRefresh?.isRefreshing = false
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(requireContext(), "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun getDataListProduct(item: List<DataProduct?>?) {
        val adapterListProduct = AdapterListProduct(requireContext(), item)
        binding?.rvProduct?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterListProduct
        }
    }

}
package project.xinyuan.sales.view.home.customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListCustomer
import project.xinyuan.sales.databinding.FragmentCustomerBinding
import project.xinyuan.sales.model.DataCustomer

class CustomerFragment : Fragment(), CustomerContract {

    private var _binding:FragmentCustomerBinding? =null
    private val binding get() = _binding
    private lateinit var presenter: CustomerPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
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

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getListCustomer()
        }
    }

    override fun messageGetListCustomer(msg: String) {
        Log.d("listCustomer", msg)
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun getListCustomer(data: List<DataCustomer?>?) {
        val adapterCustomer = AdapterListCustomer(requireContext(), data)
        binding?.rvCustomer?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterCustomer
        }
    }

}
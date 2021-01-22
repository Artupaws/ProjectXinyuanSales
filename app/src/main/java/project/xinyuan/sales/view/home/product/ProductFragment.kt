package project.xinyuan.sales.view.home.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import project.xinyuan.sales.adapter.AdapterListProduct
import project.xinyuan.sales.databinding.FragmentProductBinding
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataProduct

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun messageGetListProduct(msg: String) {
        Log.d("listProduct", msg)
    }

    override fun getDataListProduct(item: List<DataProduct?>?) {
        val adapterListProduct = AdapterListProduct(requireContext(), item)
        binding?.rvProduct?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterListProduct
        }
    }

}
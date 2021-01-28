package project.xinyuan.sales.view.addfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.FragmentAddBinding
import project.xinyuan.sales.view.addfragment.addcustomerdata.DataCustomerActivty
import project.xinyuan.sales.view.addfragment.addordercustomer.AddOrderCustomerActivity
import project.xinyuan.sales.view.addfragment.choosecustomer.ChooseCustomerActivity

class AddFragment : Fragment(), View.OnClickListener {

    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.linearAddCustomer?.setOnClickListener(this)
        binding?.cvAddOrderCustomer?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.linear_add_customer -> {
                val intent = Intent(requireContext(), DataCustomerActivty::class.java)
                startActivity(intent)
            }
            R.id.cv_add_order_customer -> {
                val intent = Intent(requireContext(), ChooseCustomerActivity::class.java)
                startActivity(intent)
            }

        }
    }

}
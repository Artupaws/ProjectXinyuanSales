package project.xinyuan.sales.view.addfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.FragmentAddCustomerBinding
import project.xinyuan.sales.view.addfragment.addcustomerdata.DataCustomerActivty

class AddCustomerFragment : Fragment(), View.OnClickListener {

    private var _binding:FragmentAddCustomerBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCustomerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.linearAddCustomer?.setOnClickListener(this)
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
        }
    }

}
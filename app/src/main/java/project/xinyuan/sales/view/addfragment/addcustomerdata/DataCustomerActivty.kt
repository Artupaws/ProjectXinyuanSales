package project.xinyuan.sales.view.addfragment.addcustomerdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityDataCustomerActivtyBinding

class DataCustomerActivty : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityDataCustomerActivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataCustomerActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Add Customer"
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
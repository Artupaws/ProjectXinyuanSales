package project.xinyuan.sales.view.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityListCartBinding

class ListCartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarListCart.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarListCart.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbarListCart.title = "Customer Order"

    }
}
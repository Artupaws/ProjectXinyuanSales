package project.xinyuan.sales.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
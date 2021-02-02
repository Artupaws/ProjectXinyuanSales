package project.xinyuan.sales.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAccountBinding
import project.xinyuan.sales.model.DataSales

class AccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSales = intent.getParcelableExtra<DataSales>("detailSales")
        binding.tvSalesName.text = dataSales?.name
        Glide.with(this).load(dataSales?.photo).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivProfile)
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
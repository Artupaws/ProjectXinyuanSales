package project.xinyuan.sales.view.history.detailtransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductDetailTransaction
import project.xinyuan.sales.databinding.ActivityDetailTransactionBinding
import project.xinyuan.sales.model.DataTransaction

class DetailTransactionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding :ActivityDetailTransactionBinding
    private var debt:Int? =null
    private var totalPrice:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetailTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailTransaction.title = "Detail Transaction"
        val dataTransaction = intent.getParcelableExtra<DataTransaction>("dataTransaction")
        totalPrice = dataTransaction?.transactiondetails?.map { it?.price.toString().toInt()*it?.quantity.toString().toInt() }?.sum()
        binding.tvTempo.text = dataTransaction?.payment
        binding.tvPostpaid.text = dataTransaction?.paymentPeriod.toString()
        binding.tvDuedate.text = dataTransaction?.paymentDeadline
        binding.tvTotalPrice.text = totalPrice.toString()
        binding.tvTotalPay.text = dataTransaction?.paid.toString()
        debt = dataTransaction?.debt
        if (debt == 0 ){
            binding.tvStatusPayment.text = "Paid Off"
        } else {
            binding.tvStatusPayment.text = debt.toString()
        }

        val adapterProduct = AdapterListProductDetailTransaction(applicationContext, dataTransaction?.transactiondetails)
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterProduct
        }
        refresh()

    }

    override fun onClick(p0: View?) {
        when(p0?.id){

        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

}
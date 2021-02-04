package project.xinyuan.sales.view.history.detailtransaction

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductDetailTransaction
import project.xinyuan.sales.databinding.ActivityDetailTransactionBinding
import project.xinyuan.sales.model.DataPayment
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.dashboard.DashboardActivity

class DetailTransactionActivity : AppCompatActivity(), View.OnClickListener, DetailTransactionContract {

    private lateinit var binding :ActivityDetailTransactionBinding
    private var debt:Int? =null
    private var totalPrice:Int? = null
    private var popupMakePayment: Dialog? = null
    private lateinit var presenter: DetailTransactionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailTransactionPresenter(this, this)
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
        binding.btnMakePayment.setOnClickListener(this)
        refresh()
        dialogPayment(dataTransaction)
        setupStatus(dataTransaction)
        setupListProduct(dataTransaction)
        stateButtonMakePayment()

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_make_payment -> {
                popupMakePayment?.show()
            }
        }
    }

    private fun setupStatus(dataTransaction: DataTransaction?){
        if (dataTransaction?.debt == 0 ){
            binding.tvStatusPayment.text = "Paid Off"
        } else {
            binding.tvStatusPayment.text = dataTransaction?.debt.toString()
        }
    }

    private fun move(){
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        intent.putExtra("openFragment", 3)
        startActivity(intent)
        finishAffinity()
    }

    private fun stateButtonMakePayment(){
        binding.btnMakePayment.isEnabled = binding.tvStatusPayment.text != "Paid Off"
    }

    private fun setupListProduct(dataTransaction: DataTransaction?){
        val adapterProduct = AdapterListProductDetailTransaction(applicationContext, dataTransaction?.transactiondetails)
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterProduct
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun dialogPayment(dataTransaction: DataTransaction?){
        @SuppressLint("UseCompatLoadingForDrawables")
        popupMakePayment = Dialog(this)
        popupMakePayment?.setContentView(R.layout.popup_make_payment)
        popupMakePayment?.setCancelable(true)
        popupMakePayment?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window: Window = popupMakePayment?.window!!
        window.setGravity(Gravity.CENTER)
        val btnPayment = popupMakePayment?.findViewById<Button>(R.id.btn_payment)
        val btnNo = popupMakePayment?.findViewById<Button>(R.id.btn_no)
        val etMustPay = popupMakePayment?.findViewById<EditText>(R.id.et_total_must_pay)
        val tvTotalpay = popupMakePayment?.findViewById<TextView>(R.id.tv_total_pay)
        val progressBar = popupMakePayment?.findViewById<ProgressBar>(R.id.progress_circular)
        tvTotalpay?.text = dataTransaction?.debt.toString()
        etMustPay?.hint = dataTransaction?.debt.toString()
        btnPayment?.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            btnNo?.isEnabled = false
            btnPayment.visibility = View.GONE
            presenter.makePaymentCustomer(dataTransaction?.idTransaction!!, etMustPay?.text.toString().toInt())
        }
        btnNo?.setOnClickListener {
            popupMakePayment?.dismiss()
        }
        etMustPay?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun afterTextChanged(p0: Editable?) {
                btnPayment?.isEnabled = (p0?.isNotEmpty()!!) && (p0.toString() == tvTotalpay?.text)
            }

        })

    }

    override fun messageMakePayment(msg: String) {
        Log.d("messageMakePayment", msg)
        popupMakePayment?.dismiss()
        if (msg.contains("Success")){
            move()
            Snackbar.make(binding.btnMakePayment, msg, Snackbar.LENGTH_SHORT).show()
        }else {
            Snackbar.make(binding.btnMakePayment, "Payment Failed.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getDataPayment(data: DataPayment?) {
        Log.d("dataPayment", data?.createdAt.toString())
    }
}
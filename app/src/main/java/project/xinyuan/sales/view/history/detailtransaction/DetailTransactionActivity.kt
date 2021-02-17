package project.xinyuan.sales.view.history.detailtransaction

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListProductDetailTransaction
import project.xinyuan.sales.adapter.AdapterSpinnerPaymentAccount
import project.xinyuan.sales.databinding.ActivityDetailTransactionBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.helper.NumberTextWatcher
import project.xinyuan.sales.model.DataPayment
import project.xinyuan.sales.model.DataPaymentAccount
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.cart.ListCartPresenter
import project.xinyuan.sales.view.dashboard.DashboardActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailTransactionActivity : AppCompatActivity(), View.OnClickListener, DetailTransactionContract {

    private lateinit var binding :ActivityDetailTransactionBinding
    private var debt:Int? =null
    private var totalPrice:Int? = null
    private var popupMakePayment: Dialog? = null
    private lateinit var presenter: DetailTransactionPresenter
    private lateinit var helper: Helper
    private var totalMustPay:String? = null
    private var idTransaction:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailTransactionPresenter(this, this)
        presenter.getPaymentAccount()
        helper = Helper()
        binding.toolbarDetailTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailTransaction.title = "Detail Transaction"
        val dataTransaction = intent.getParcelableExtra<DataTransaction>("dataTransaction")
        totalMustPay = helper.convertToFormatMoneyIDR(dataTransaction.debt.toString())
        idTransaction = dataTransaction?.idTransaction
        totalPrice = dataTransaction?.transactiondetails?.map { it?.price.toString().toInt()*it?.quantity.toString().toInt() }?.sum()
        binding.tvTempo.text = dataTransaction?.payment
        binding.tvPostpaid.text = dataTransaction?.paymentPeriod.toString()
        binding.tvDuedate.text = dataTransaction?.paymentDeadline
        binding.tvTotalPrice.text = totalPrice.toString()
        binding.tvTotalPay.text = dataTransaction?.paid.toString()
        debt = dataTransaction?.debt
        binding.btnMakePayment.setOnClickListener(this)
        refresh()
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

    private fun dialogPayment(listPaymentAccount: List<DataPaymentAccount?>?){
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
        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val twSalaryFrom: TextWatcher = NumberTextWatcher(etMustPay!!, locale, numDecs)
        val spinnerPaymentAccount = popupMakePayment?.findViewById<Spinner>(R.id.spn_payment_account)
        var account:Int? = null
        tvTotalpay?.text = totalMustPay
        btnPayment?.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            btnNo?.isEnabled = false
            btnPayment.visibility = View.GONE
            presenter.makePaymentCustomer(idTransaction!!, helper.changeFormatMoneyToValue(etMustPay.text.toString()).toInt(), account!!)
        }
        btnNo?.setOnClickListener {
            popupMakePayment?.dismiss()
        }
        etMustPay.addTextChangedListener(twSalaryFrom)
        etMustPay.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun afterTextChanged(p0: Editable?) {
                btnPayment?.isEnabled = p0?.isNotEmpty()!!&&account!=null
            }

        })
        spinnerPaymentAccount?.adapter = AdapterSpinnerPaymentAccount(applicationContext, listPaymentAccount)
        spinnerPaymentAccount?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                account = listPaymentAccount?.get(p2)?.id
                btnPayment?.isEnabled = etMustPay.text.isNotEmpty()&&account!=null
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                false
            }

        }

    }

    override fun messageGetPaymentAccount(msg: String) {
        Log.d("getPaymentAccount", msg)
        binding.swipeRefresh.isRefreshing = false
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
        Log.d("dataPayment", data?.date.toString())
    }

    override fun getPaymentAccount(data: List<DataPaymentAccount?>?) {
        dialogPayment(data)
    }
}
package project.xinyuan.sales.view.history.detailtransaction

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import project.xinyuan.sales.adapter.AdapterSpinnerBankName
import project.xinyuan.sales.adapter.AdapterSpinnerPaymentAccount
import project.xinyuan.sales.databinding.ActivityDetailTransactionBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.helper.NumberTextWatcher
import project.xinyuan.sales.model.*
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
    private var popupPayWithGiro: Dialog? = null
    private lateinit var presenter: DetailTransactionPresenter
    private lateinit var helper: Helper
    private var totalMustPayIDR:String? = null
    private var totalMustPayValue:String? = null
    private var idTransaction:Int? = null
    private var idBank:Int? = null
    private var datePayment:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DetailTransactionPresenter(this, this)
        presenter.getPaymentAccount()
        presenter.getBankName()
        helper = Helper()
        binding.toolbarDetailTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailTransaction.title = "Detail Transaction"
        val dataTransaction = intent.getParcelableExtra<DataTransaction>("dataTransaction")
        totalMustPayIDR = helper.convertToFormatMoneyIDR(dataTransaction.debt.toString())
        totalMustPayValue = dataTransaction?.debt.toString()
        idTransaction = dataTransaction?.idTransaction
        totalPrice = dataTransaction?.transactiondetails?.map { it?.price.toString().toInt()*it?.quantity.toString().toInt() }?.sum()
        binding.tvTempo.text = dataTransaction?.payment
        binding.tvPostpaid.text = dataTransaction?.paymentPeriod.toString()
        binding.tvDuedate.text = dataTransaction?.paymentDeadline
        binding.tvTotalPrice.text = helper.convertToFormatMoneyIDR(totalPrice.toString())
        binding.tvTotalPay.text = helper.convertToFormatMoneyIDR(dataTransaction?.paid.toString())
        debt = dataTransaction?.debt
        binding.btnMakePayment.setOnClickListener(this)
        refresh()
        setupStatus(dataTransaction)
        setupListProduct(dataTransaction)
        setDate()
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
            binding.linearRemainingDebt.visibility = View.GONE
            binding.tvStatusPayment.text = getString(R.string.paid_off)
            binding.tvStatusPayment.isEnabled = true
        } else {
            binding.linearRemainingDebt.visibility = View.VISIBLE
            binding.tvStatusPayment.text = getString(R.string.not_paid_off)
            binding.tvStatusPayment.isEnabled = false
            binding.tvRemainingDebt.text = helper.convertToFormatMoneyIDR(dataTransaction?.debt.toString())
        }
        stateButtonMakePayment()
    }

    private fun move(){
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        intent.putExtra("openFragment", 3)
        startActivity(intent)
        finishAffinity()
    }

    private fun stateButtonMakePayment(){
        binding.btnMakePayment.isEnabled = binding.tvStatusPayment.text != getString(R.string.paid_off)
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
            presenter.getPaymentAccount()
            presenter.getBankName()
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
        val etMustPay = popupMakePayment?.findViewById<EditText>(R.id.et_total_must_pay)
        val tvTotalpay = popupMakePayment?.findViewById<TextView>(R.id.tv_total_pay)
        val progressBar = popupMakePayment?.findViewById<ProgressBar>(R.id.progress_circular)
        val addGiro = popupMakePayment?.findViewById<RelativeLayout>(R.id.relative_action_add)
        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val twSalaryFrom: TextWatcher = NumberTextWatcher(etMustPay!!, locale, numDecs)
        val spinnerPaymentAccount = popupMakePayment?.findViewById<Spinner>(R.id.spn_payment_account)
        var account:Int? = null
        tvTotalpay?.text = totalMustPayIDR
        btnPayment?.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            btnPayment.visibility = View.GONE
            presenter.makePaymentCustomer(idTransaction!!, helper.changeFormatMoneyToValue(etMustPay.text.toString()).toInt(), account!!, datePayment!!)
        }

        etMustPay.addTextChangedListener(twSalaryFrom)
        etMustPay.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty()!!){
                    btnPayment?.isEnabled = (totalMustPayValue?.toLong()!!.minus(helper.changeFormatMoneyToValue(p0.toString()).toLong()) >= 0)
                } else {
                    btnPayment?.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                false
            }
        })

        spinnerPaymentAccount?.adapter = AdapterSpinnerPaymentAccount(applicationContext, listPaymentAccount)
        spinnerPaymentAccount?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                account = listPaymentAccount?.get(p2)?.id
                btnPayment?.isEnabled = etMustPay.text.isNotEmpty()&&account!=null
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        addGiro?.setOnClickListener {
//            Snackbar.make(addGiro, "Sorry, this feature under development", Snackbar.LENGTH_SHORT).show()
            popupMakePayment?.dismiss()
            popupPayWithGiro?.show()
        }
    }

    private fun setDate(){
        datePayment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val currentDate = LocalDateTime.now()
            val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = currentDate.format(formatDate)
            date
        }else{
            val currentDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.format(currentDate)
            date
        }
    }

    private fun dialogPaymentGiro(dataBank: List<DataBank?>?){
        @SuppressLint("UseCompatLoadingForDrawables")
        popupPayWithGiro = Dialog(this)
        popupPayWithGiro?.setContentView(R.layout.popup_payment_giro)
        popupPayWithGiro?.setCancelable(true)
        popupPayWithGiro?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window: Window = popupPayWithGiro?.window!!
        window.setGravity(Gravity.CENTER)
        val spinnerBankName = popupPayWithGiro?.findViewById<Spinner>(R.id.spn_bank_name)
        val giroNumber = popupPayWithGiro?.findViewById<EditText>(R.id.et_giro_number)
        val balance = popupPayWithGiro?.findViewById<EditText>(R.id.et_balance)
        val addGiro = popupPayWithGiro?.findViewById<Button>(R.id.btn_add_giro)
        val loading = popupPayWithGiro?.findViewById<ProgressBar>(R.id.progress_circular)
        val error = popupPayWithGiro?.findViewById<TextView>(R.id.tv_empty)
        val date = popupPayWithGiro?.findViewById<EditText>(R.id.et_day)
        val month = popupPayWithGiro?.findViewById<EditText>(R.id.et_month)
        val year = popupPayWithGiro?.findViewById<EditText>(R.id.et_year)
        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val formatMoney: TextWatcher = NumberTextWatcher(balance!!, locale, numDecs)

        spinnerBankName?.adapter = AdapterSpinnerBankName(applicationContext, dataBank)
        spinnerBankName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                idBank = dataBank?.get(position)?.id!!
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                idBank = 0
            }
        }

        balance.addTextChangedListener(formatMoney)

        addGiro?.setOnClickListener {
            val giro = giroNumber?.text.toString()
            val saldo = balance.text.toString()
            val bankName = idBank
            val day = date?.text.toString()
            val monthh = month?.text.toString()
            val yearr = year?.text.toString()
            val dateReceive = "$day-$monthh-$yearr"

            if (giro.isEmpty() || saldo.isEmpty() || dateReceive.isEmpty() || bankName==0 || day.isEmpty() || monthh.isEmpty() || yearr.isEmpty()){
                error?.visibility = View.VISIBLE
                error?.text = "Please complete all field"
            } else {
                presenter.addTransactionGiro(idTransaction!!, idBank!!, giroNumber?.text.toString(), helper.changeFormatMoneyToValue(balance.text.toString()).toLong(), dateReceive)
                addGiro.visibility = View.GONE
                loading?.visibility = View.VISIBLE
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

    override fun messageGetBankName(msg: String) {
        Log.d("bank", msg)
    }

    override fun messageAddTransactionGiro(msg: String) {
        Log.d("addGiro", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Success")){
            popupPayWithGiro?.dismiss()
            onBackPressed()
        } else {
            popupPayWithGiro?.dismiss()
        }
    }

    override fun getDataPayment(data: DataPayment?) {
        Log.d("dataPayment", data?.date.toString())
    }

    override fun getPaymentAccount(data: List<DataPaymentAccount?>?) {
        dialogPayment(data)
    }

    override fun getBankName(data: MutableList<DataBank?>?) {
        val bankName = mutableListOf<DataBank?>()
        bankName.addAll(listOf(DataBank("", "Choose", "", 0)))
        bankName.addAll(data!!)
        dialogPaymentGiro(bankName)
    }
}
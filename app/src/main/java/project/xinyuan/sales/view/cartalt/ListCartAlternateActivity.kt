package project.xinyuan.sales.view.cartalt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListCart
import project.xinyuan.sales.adapter.AdapterListCartAlternate
import project.xinyuan.sales.adapter.AdapterSpinnerPaymentAccount
import project.xinyuan.sales.databinding.ActivityListCartAlternateBinding
import project.xinyuan.sales.databinding.ActivityListCartBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.company.master.CompanyItem
import project.xinyuan.sales.model.customer.master.DataCustomer
import project.xinyuan.sales.model.transaction.master.DataFormalTransaction
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount
import project.xinyuan.sales.model.transaction.ProductItem
import project.xinyuan.sales.model.transaction.RequestCreateTransaction
import project.xinyuan.sales.model.warehouse.master.DataWarehouse
import project.xinyuan.sales.roomdatabase.CartDao
import project.xinyuan.sales.roomdatabase.CartItem
import project.xinyuan.sales.roomdatabase.CartRoomDatabase
import project.xinyuan.sales.view.dashboard.DashboardActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ListCartAlternateActivity : AppCompatActivity(), ListCartAlternateContract, View.OnClickListener {

    private lateinit var binding : ActivityListCartAlternateBinding
    private val spinnerTempo = arrayOf("Choose", "cash", "postpaid")
    private val spinnerPostpaid = arrayOf("Choose", "30", "45", "60", "70", "90")
    private lateinit var presenter: ListCartAlternatePresenter
    private lateinit var sharedPref:SharedPreferencesHelper
    private lateinit var database: CartRoomDatabase
    private lateinit var dao: CartDao
    var idCustomer:Int? = null
    var idTransaction:Int? = null
    var totalPrice:Int?=null
    var isEmptyInvoice = true
    var isEmptyPayment = true
    var isEmptyPaymenPeriod = true
    var isEmptyDoNumber = true
    private var paymentType:String? = null
    private var paymentPeriod:String?=null
    private var account:Int = 0
    private var valuePaymentCash:Int = 0
    private lateinit var listItemCart: ArrayList<CartItem>
    private val helper: Helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCartAlternateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getListCartData()
        presenter = ListCartAlternatePresenter(this, this)
        sharedPref = SharedPreferencesHelper(this)
        database = CartRoomDatabase.getDatabase(this)
        dao = database.getCartDao()
        binding.toolbarListCart.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarListCart.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnApprove.setOnClickListener(this)
        binding.toolbarListCart.title = "Customer Order"


        val detailCustomer = intent.getParcelableExtra<DataCustomer>("detailCustomer")
        idCustomer = detailCustomer?.id
        setCustomerData(detailCustomer)

        setDate()
        setupSpinnerTempo()
        setupSpinnerPostpaid()
        presenter.getPaymentAccount()

    }
    private fun setCustomerData(data:DataCustomer){
        binding.tvCompanyName.text = data.companyName
        binding.tvAdminName.text = data.administratorName
        binding.tvAdminPhone.text = data.administratorPhone
    }

    private fun getListCartData(){
        val database = CartRoomDatabase.getDatabase(applicationContext)
        val dao = database.getCartDao()
        val listItems = arrayListOf<CartItem>()
        listItems.addAll(dao.getAll())
        setupListCart(listItems)
    }

    private fun setDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val currentDate = LocalDateTime.now()
            val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = currentDate.format(formatDate)
            binding.tvTransactionDate.text = date
        }else{
            val currentDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.format(currentDate)
            binding.tvTransactionDate.text = date
        }
    }

    private fun setupListCart(listItem: ArrayList<CartItem>){
        listItemCart = listItem
        binding.rvCartOrder.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterListCartAlternate(applicationContext, listItem)
            totalPrice = listItem.map { it.total.toInt()*it.price.toInt() }.sum()
            binding.tvTotalPrice.text = totalPrice.toString()
        }
    }

    private fun setupSpinnerTempo(){
        val arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, spinnerTempo)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTempo.adapter = arrayAdapter
        binding.spnTempo.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                paymentType = binding.spnTempo.selectedItem.toString()
                Log.d("tempo", paymentType)
                binding.btnApprove.isEnabled = paymentType != "Choose"
                stateUnloading()
                when (paymentType) {
                    "postpaid" -> {
                        binding.clPostpaid.visibility = View.VISIBLE
                        binding.clPaymentAccount.visibility = View.GONE
                        valuePaymentCash = 0
                    }
                    "cash" -> {
                        paymentPeriod = "0"
                        valuePaymentCash = helper.changeFormatMoneyToValue(binding.tvTotalPrice.text.toString()).toInt()
                        binding.clPostpaid.visibility = View.GONE
                        binding.clPaymentAccount.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.clPostpaid.visibility = View.GONE
                        binding.clPaymentAccount.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setupSpinnerPaymentAccount(listPaymentAccount: List<DataPaymentAccount?>?){
        val arrayAdapter = AdapterSpinnerPaymentAccount(this, listPaymentAccount)
        binding.spnPaymentAccount.adapter = arrayAdapter
        binding.spnPaymentAccount.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                account = listPaymentAccount?.get(position)?.id!!
                binding.btnApprove.isEnabled = account != null
                stateUnloading()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                paymentType = null
            }
        }
    }

    private fun setupSpinnerPostpaid(){
        val arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, spinnerPostpaid)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnPostpaid.adapter = arrayAdapter
        binding.spnPostpaid.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                paymentPeriod = binding.spnPostpaid.selectedItem.toString()
                Log.d("postpaid", paymentPeriod)
                if (paymentPeriod != "Choose"){
                    binding.btnApprove.isEnabled = true
                    account = 0
                }else{
                    binding.btnApprove.isEnabled = false
                }
                stateUnloading()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                paymentType = null
            }
        }
    }

    private fun checkAddDataTransaction(){
        var invoiceNumber = binding.etNumberInvoice.text.toString()
        var paymentFill = paymentType

        if (invoiceNumber.isEmpty()){
            isEmptyInvoice = true
            binding.etNumberInvoice.error = "empty"
        } else {
            isEmptyInvoice = false
            invoiceNumber = binding.etNumberInvoice.text.toString()
        }

        if (paymentFill == "Choose"){
            isEmptyPayment = true
            Snackbar.make(binding.btnApprove, "please choose tempo", Snackbar.LENGTH_SHORT).show()
        } else {
            isEmptyPayment = false
            paymentFill = paymentType
        }

        if (!isEmptyInvoice && !isEmptyPayment){
            Log.d("data", "$invoiceNumber $idCustomer $paymentType $paymentPeriod $valuePaymentCash ${helper.changeFormatMoneyToValue(binding.tvTotalPrice.text.toString()).toInt()} $account")
//            presenter.addDataFormalTransaction(invoiceNumber, idCustomer!!, paymentType!!, paymentPeriod!!.toInt(), valuePaymentCash, helper.changeFormatMoneyToValue(binding.tvTotalPrice.text.toString()).toInt(), account, binding.tvTransactionDate.text.toString())
            var listProduct:ArrayList<ProductItem?>? = ArrayList()
            for (i in listItemCart){
                listProduct!!.add(ProductItem(i.subTotal.toInt(),i.id,i.total.toInt(),i.price.toInt()))
            }
            val request = RequestCreateTransaction(invoiceNumber,idCustomer,binding.tvTransactionDate.text.toString(),4,1,paymentPeriod!!.toInt(),paymentType,helper.changeFormatMoneyToValue(binding.tvTotalPrice.text.toString()).toInt(),listProduct,account)
            presenter.addTransaction(request)
        } else {
            stateUnloading()
            Snackbar.make(binding.btnApprove, "please complete form", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_approve -> {
                stateLoading()
                checkAddDataTransaction()
            }
        }
    }

    private fun stateLoading(){
        binding.btnApprove.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun stateUnloading(){
        binding.btnApprove.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
    }

    private fun move(){
        dao.deleteAll()
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        startActivity(intent)
    }

    override fun messageAddProductTransaction(msg: String) {
        Log.d("addProductTransaction", msg)
        if (msg.contains("Success")){
            move()
            stateUnloading()
        }else {
            stateUnloading()
        }
    }

    override fun messageGetPaymentAccount(msg: String) {
        Log.d("getPaymentAccount", msg)
    }

    override fun messageAddDataFormalTransaction(msg: String) {
        Log.d("addDataTransaction", msg)
    }

    override fun getDataFormalTransaction(data: DataFormalTransaction?) {
        Log.d("idTransaction", data?.id.toString())
        idTransaction = data?.id

        move()
        stateUnloading()
    }

    override fun getPaymentAccount(data: List<DataPaymentAccount?>?) {
        setupSpinnerPaymentAccount(data)
    }

    override fun getWarehouse(data: List<DataWarehouse?>?) {

    }

    override fun getCompany(data: List<CompanyItem?>?) {
    }


}
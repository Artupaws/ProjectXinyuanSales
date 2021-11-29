package project.xinyuan.sales.view.addfragment.addcustomerdata

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterListArea
import project.xinyuan.sales.adapter.AdapterSpinnerLevelCustomer
import project.xinyuan.sales.databinding.ActivityRegisterCustomerBinding
import project.xinyuan.sales.model.customer.master.CustomerLevel
import project.xinyuan.sales.model.area.master.DataArea
import project.xinyuan.sales.model.customer.master.DataCustomer
import project.xinyuan.sales.view.addfragment.addcustomerphoto.AddPhotoCustomerActivity
import java.util.*

class DataCustomerActivty : AppCompatActivity(), View.OnClickListener, DataCustomerContract {

    private lateinit var binding:ActivityRegisterCustomerBinding
    private lateinit var presenter:DataCustomerPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idArea:Int = 0
    private var popupListArea: Dialog? = null
    private var popupLoading: Dialog? = null
    private var idCustomerRegistered:Int? = null
    private var idCardAvailable = false
    private var isEmptyIdArea = true
    private var isEmptyCompanyName = true
    private var isEmptyCompanyAddress = true
    private var isEmptyAdminName = true
    private var isEmptyAdminId = true
    private var isEmptyAdminPhone = true
    private var isEmptyCompanyPhone = true
    private var isEmptyCompanyNpwp = true
    private var isEmptyLevelCustomer = true
    private var isEmptyAreaId = true
    private var placeBirth:String?=null
    private var dateBirth:String?=null
    private var idLevelCustomer:Int=0
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DataCustomerPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)

        binding.toolbar.title = "Add Customer"
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.btnRegisterCustomer.setOnClickListener(this)
        binding.etArea.setOnClickListener(this)
        binding.etDateBirth.setOnClickListener(this)
        binding.etPlaceBirth.addTextChangedListener(getTextWatcher(binding.etPlaceBirth))
        binding.etDateBirth.addTextChangedListener(getTextWatcher(binding.etDateBirth))
        binding.etArea.addTextChangedListener(getTextWatcher(binding.etArea))

        presenter.getListDataArea()
        presenter.getCustomerLevel()
        popupLoading()
        popupLoading?.show()
        refresh()
        setTextBirthDate()

        binding.etIdcardAdmin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty()!!){
                    binding.etIdcardAdmin.error = null
                    true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty()!!){
                    presenter.checkIdCustomer(s.toString())
                    true
                }
            }

        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.et_area -> {
                popupListArea?.show()
            }
            R.id.et_date_birth ->{
                setDateBirth()
            }
            R.id.btn_register_customer -> {
                popupLoading?.show()
                checkDataCustomer()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        popupLoading?.dismiss()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("areaCustomer"))
    }

    private val mMessageReceiver: BroadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "areaCustomer" -> {
                    val areaId = intent.getIntExtra("areaId",0)
                    idArea = areaId
                    Log.d("idArea", idArea.toString())
                    val areaName = intent.getStringExtra("areaName")
                    binding.etArea.setText(areaName)
                    popupListArea?.dismiss()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun checkDataCustomer(){
        val area:String = binding.etArea.text.toString()
        var companyName = binding.etCompanyName.text.toString()
        var companyAddress = binding.etCompanyAddress.text.toString()
        var companyNpwp = binding.etCompanyNpwp.text.toString()
        var companyPhone = binding.etCompanyPhone.text.toString()
        var nameAdmin = binding.etNameAdminCompany.text.toString()
        var idCardAdmin = binding.etIdcardAdmin.text.toString()
        var phoneAdmin = binding.etPhoneNumber.text.toString()
        var levelCustomer = idLevelCustomer
        val npwpAdmin:String? = null
        val addressAdmin:String? = null

        if (area.isEmpty()){
            isEmptyIdArea = true
            binding.etArea.error = "Empty"
        }else{
            isEmptyIdArea = false
        }

        if (companyName.isEmpty()){
            isEmptyCompanyName = true
            binding.etCompanyName.error = "Empty"
        } else {
            companyName = binding.etCompanyName.text.toString()
            isEmptyCompanyName = false
        }

        if (companyAddress.isEmpty()){
            isEmptyCompanyAddress = true
            binding.etCompanyAddress.error = "Empty"
        } else {
            isEmptyCompanyAddress = false
            companyAddress = binding.etCompanyAddress.text.toString()
        }

        if (companyNpwp.isEmpty()){
            isEmptyCompanyNpwp = true
            binding.etCompanyNpwp.error = "Empty"
        } else {
            isEmptyCompanyNpwp = false
            companyNpwp = binding.etCompanyNpwp.text.toString()
        }

        if (companyPhone.isEmpty()){
            isEmptyCompanyPhone = true
            binding.etCompanyPhone.error = "Empty"
        } else {
            isEmptyCompanyPhone = false
            companyPhone = binding.etCompanyPhone.text.toString()
        }

        if (nameAdmin.isEmpty()){
            isEmptyAdminName = true
            binding.etNameAdminCompany.error = "Empty"
        } else {
            isEmptyAdminName = false
            nameAdmin = binding.etNameAdminCompany.text.toString()
        }

        if (idCardAdmin.isEmpty()){
            isEmptyAdminId = true
            binding.etIdcardAdmin.error = "Empty"
        }else {
            isEmptyAdminId = false
            idCardAdmin = binding.etIdcardAdmin.text.toString()
        }

        if (phoneAdmin.isEmpty()){
            isEmptyAdminPhone = true
            binding.etPhoneNumber.error = "Empty"
        }else{
            isEmptyAdminPhone = false
            phoneAdmin = binding.etPhoneNumber.text.toString()
        }

        if (levelCustomer == 0){
            isEmptyLevelCustomer = true
        }else {
            isEmptyLevelCustomer = false
            levelCustomer = idLevelCustomer
        }

        if (!isEmptyIdArea && !isEmptyCompanyName && !isEmptyCompanyAddress && !isEmptyCompanyNpwp && !isEmptyCompanyPhone
                && !isEmptyAdminName && !isEmptyAdminId && !isEmptyAdminPhone && !isEmptyLevelCustomer){
            move(idArea, companyName, companyAddress, nameAdmin, idCardAdmin, phoneAdmin, companyPhone, companyNpwp, addressAdmin.toString(), "$placeBirth, $dateBirth", npwpAdmin.toString(), idLevelCustomer)
        } else {
            popupLoading?.dismiss()
            Snackbar.make(binding.btnRegisterCustomer, "Please complete form", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun move(idArea:Int, companyName:String, companyAddress:String, nameAdmin:String, idCardAdmin:String,
                     phoneAdmin:String, companyPhone:String, companyNpwp:String, addressAdmin:String, placeAndBirthAdmin:String, npwpAdmin:String, idLevel:Int){
        val intent = Intent(applicationContext, AddPhotoCustomerActivity::class.java)
        intent.putExtra("idArea", idArea)
        intent.putExtra("companyName", companyName)
        intent.putExtra("companyAddress", companyAddress)
        intent.putExtra("idCardAdmin", idCardAdmin)
        intent.putExtra("phoneAdmin", phoneAdmin)
        intent.putExtra("companyPhone", companyPhone)
        intent.putExtra("companyNpwp", companyNpwp)
        intent.putExtra("addressAdmin", addressAdmin)
        intent.putExtra("placeAndBirthAdmin", placeAndBirthAdmin)
        intent.putExtra("nameAdmin", nameAdmin)
        intent.putExtra("npwpAdmin", npwpAdmin)
        intent.putExtra("idLevel", idLevel)
        startActivity(intent)
    }

    private fun refresh(){
        binding.refresh.setOnRefreshListener {
            presenter.getListDataArea()
            presenter.getCustomerLevel()
        }
    }

    private fun setDateBirth(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                year, month, day)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun setTextBirthDate(){
        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month += 1
            Log.d("dateBirth", "$day-$month-$year")
            val date = "$day-$month-$year"
            binding.etDateBirth.setText(date)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupLoading(){
        popupLoading = Dialog(this)
        popupLoading?.setContentView(R.layout.popup_loading)
        popupLoading?.setCancelable(true)
        popupLoading?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window:Window = popupLoading?.window!!
        window.setGravity(Gravity.CENTER)
        popupLoading?.show()
    }

    private fun getTextWatcher(editText: EditText):TextWatcher{
        return object :TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                false
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etPlaceBirth.text.hashCode() == s.hashCode()){
                    if (s?.isNotEmpty()!!){
                        placeBirth = binding.etPlaceBirth.text.toString()
                        binding.etDateBirth.isEnabled = true
                    } else {
                        binding.etDateBirth.text = null
                        binding.etDateBirth.isEnabled = false
                    }
                } else if (binding.etDateBirth.text.hashCode() == s.hashCode()){
                    if (s?.isNotEmpty()!!){
                        dateBirth = binding.etDateBirth.text.toString()
                    }
                } else if (binding.etArea.text.hashCode() == s.hashCode()){
                    if (s?.isEmpty()!!){
                        binding.etArea.error = null
                    }
                }
                true
            }
        }
    }

    private fun setupSpinnerLevel(dataLevel: List<CustomerLevel?>?){
        val arrayAdapter = AdapterSpinnerLevelCustomer(this, dataLevel)
        binding.spnLevelSales.adapter = arrayAdapter
        binding.spnLevelSales.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLevelCustomer = dataLevel?.get(position)?.id!!
                binding.btnRegisterCustomer.isEnabled = idLevelCustomer != 0
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                isEmptyLevelCustomer = true
                idLevelCustomer = 0
            }

        }
    }

    override fun messageCheckIdCustomer(msg: String) {
        if (msg.contains("Available")){
            idCardAvailable = true
        } else {
            binding.etIdcardAdmin.error = "Id card already used"
            idCardAvailable = false
        }
    }

    override fun messageRegisterDataCustomer(msg: String) {
        Log.d("registerDataCustomer", msg)
        if (msg.contains("Success")){
            popupLoading?.dismiss()
//            clearAllText()
//            move()
        } else {
            popupLoading?.dismiss()
        }
    }

    override fun messageGetListArea(msg: String) {
        Log.d("getListArea", msg)
        popupLoading?.dismiss()
        binding.refresh.isRefreshing = false
    }

    override fun messageGetCustomerLevel(msg: String) {
        Log.d("getLevelCustomer", msg)
    }

    override fun getIdCustomer(idCustomer: DataCustomer?) {
        idCustomerRegistered = idCustomer?.id
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getListArea(item: List<DataArea?>?) {
        val adapterListArea = AdapterListArea(this, item)
        popupListArea = Dialog(this)
        popupListArea?.setContentView(R.layout.popup_list_area)
        popupListArea?.setCancelable(false)
        popupListArea?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window:Window = popupListArea?.window!!
        window.setGravity(Gravity.CENTER)
        val cancel = popupListArea?.findViewById<Button>(R.id.btn_cancel)
        cancel?.setOnClickListener { popupListArea?.dismiss() }
        val listArea = popupListArea?.findViewById<RecyclerView>(R.id.rv_list_area)
        listArea?.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = adapterListArea
        }
    }

    override fun getCustomerLevel(dataLevel: MutableList<CustomerLevel?>?) {
        val level = mutableListOf<CustomerLevel?>()
        level.addAll(listOf(CustomerLevel(null, null, "Choose", "", null, 0, null)))
        level.addAll(dataLevel!!)
        setupSpinnerLevel(level)
    }

}
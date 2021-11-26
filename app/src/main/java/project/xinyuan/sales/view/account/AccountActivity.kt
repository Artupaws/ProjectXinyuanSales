package project.xinyuan.sales.view.account

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAccountBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.sales.master.DataSales
import project.xinyuan.sales.view.login.LoginActivity
import java.io.File

class AccountActivity : AppCompatActivity(), View.OnClickListener, AccountActivityContract {

    private lateinit var binding:ActivityAccountBinding
    private var popupLogout:Dialog? = null
    private lateinit var sharedPref: SharedPreferencesHelper
    private lateinit var presenter: AccountActivityPresenter
    private var name:String? = null
    private var email:String? = null
    private var phone:String? = null
    private var address:String? = null
    private var gender:String? = null
    private var idArea:Int? = null
    var isEmptyName = true
    var isEmptyEmail = true
    var isEmptyPhone = true
    var isEmptyAddress = true
    var isEmptyGender = true
    private var genderRadio:String? = null
    private var popupLoading: Dialog? = null
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AccountActivityPresenter(this, this)
        sharedPref = SharedPreferencesHelper(applicationContext)
        val dataSales = intent.getParcelableExtra<DataSales>("detailSales")
        name = dataSales?.name
        email = dataSales?.email
        phone = dataSales?.phone
        address = dataSales?.address
        gender = dataSales?.gender
        genderRadio = dataSales?.gender
        idArea = dataSales?.idArea
        setPhotoProfile(dataSales?.photo)
        binding.ivBack.setOnClickListener(this)
        binding.tvActionLogout.setOnClickListener(this)
        binding.linearEdit.setOnClickListener(this)
        binding.linearSave.setOnClickListener(this)
        binding.ivSelectImage.setOnClickListener(this)
        showPopupLogout()
        loadingUpload()
        setDataProfile()
        radioGrroup()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_action_logout -> {
                popupLogout?.show()
            }
            R.id.linear_edit ->{
                stateEdit()
            }
            R.id.linear_save ->{
                stateCancelEdit()
            }
            R.id.iv_select_image -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, AccountActivity.PERMISSION_CODE)
                    } else {
                        //permission already granted
                        pickImageFromGallery()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickImageFromGallery()
                }
            }
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            binding.ivProfile.setImageURI(data?.data)
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri, this)
            val file = File(filepath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("photo", file.name, mFile)
            presenter.updatePhotoProfile(body)
            popupLoading?.show()
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, context: Context): String? {
        val cursor: Cursor? = context.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AccountActivity.IMAGE_PICK_CODE)
    }

    private fun stateCancelEdit(){
        if ((binding.etName.text.toString() == name)
                && binding.etEmail.text.toString() == email
                && binding.etPhone.text.toString() == phone
                && binding.etAddress.text.toString() == address
                && genderRadio == gender){
            stateSave()
        } else {
            checkDataProfile()
        }
    }

    private fun setDataProfile(){
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvPhone.text = phone
        binding.tvAddress.text = address
        binding.etName.setText(name)
        binding.etEmail.setText(email)
        binding.etPhone.setText(phone)
        binding.etAddress.setText(address)
        binding.tvGender.text = gender
        when (binding.tvGender.text) {
            "male" -> {
                binding.rbMale.isChecked = true
            }
            "female" -> {
                binding.rbFemale.isChecked = true
            }
            else -> {
                binding.rbFemale.isChecked = false
                binding.rbMale.isChecked = false
            }
        }
    }

    private fun checkDataProfile(){
        var name = binding.etName.text.toString()
        var email = binding.etEmail.text.toString()
        var phone = binding.etPhone.text.toString()
        var address = binding.etAddress.text.toString()
        var genderString = genderRadio

        if (name.isEmpty()){
            binding.etName.error = "name cannot empty"
            isEmptyName = true
        } else {
            name = binding.etName.text.toString()
            isEmptyName = false
        }

        if (email.isEmpty()){
            binding.etEmail.error = "email cannot empty"
            isEmptyEmail = true
        } else {
            email = binding.etEmail.text.toString()
            isEmptyEmail = false
        }

        if (phone.isEmpty()){
            binding.etPhone.error = "phone cannot empty"
            isEmptyPhone = true
        } else {
            phone = binding.etPhone.text.toString()
            isEmptyPhone = false
        }

        if (address.isEmpty()){
            binding.etAddress.error = "address cannot empty"
            isEmptyAddress = true
        } else {
            address = binding.etAddress.text.toString()
            isEmptyAddress = false
        }

        if (genderString == null){
            binding.rbMale.error = "gender cannot empty"
            binding.rbFemale.error = "gender cannot empty"
            isEmptyGender = true
        } else {
            genderString = genderRadio
            isEmptyGender = false
        }

        if (!isEmptyName && !isEmptyEmail && !isEmptyPhone && !isEmptyAddress && !isEmptyGender){
            presenter.updateProfile(name, email, phone, address, genderString!!, idArea!!)
            popupLoading?.show()
        } else {
            Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
        }
    }

    private fun radioGrroup(){
        binding.rdGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { _, i ->
                    val radio: RadioButton = findViewById(i)
                    radioButton(radio)
                }
        )
    }

    private fun radioButton(view: View){
        val radio: RadioButton = findViewById(binding.rdGroup.checkedRadioButtonId)
        genderRadio = radio.text.toString()
        if(genderRadio!!.isNotEmpty()){
            binding.rbMale.error = null
            binding.rbFemale.error = null
            isEmptyGender = false
        }
    }

    private fun setPhotoProfile(photoFile:String?){
        Log.d("photoProfile", photoFile)
        if (photoFile?.contains(".png")!! || (photoFile.contains(".jpeg") || (photoFile.contains(".jpg")))){
            Glide.with(this).load(photoFile).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivProfile)
        } else {
            binding.ivProfile.setImageResource(R.drawable.no_image)
        }
    }

    private fun stateEdit(){
        binding.linearEdit.visibility = View.GONE
        binding.linearSave.visibility = View.VISIBLE
        binding.etName.visibility = View.VISIBLE
        binding.etEmail.visibility = View.VISIBLE
        binding.etPhone.visibility = View.VISIBLE
        binding.etAddress.visibility = View.VISIBLE
        binding.tvName.visibility = View.GONE
        binding.tvEmail.visibility = View.GONE
        binding.tvPhone.visibility = View.GONE
        binding.tvAddress.visibility = View.GONE
        binding.tvGender.visibility = View.GONE
        binding.rdGroup.visibility = View.VISIBLE
    }

    private fun stateSave(){
        binding.linearSave.visibility = View.GONE
        binding.linearEdit.visibility = View.VISIBLE
        binding.etName.visibility = View.GONE
        binding.etEmail.visibility = View.GONE
        binding.etPhone.visibility = View.GONE
        binding.etAddress.visibility = View.GONE
        binding.tvName.visibility = View.VISIBLE
        binding.tvEmail.visibility = View.VISIBLE
        binding.tvPhone.visibility = View.VISIBLE
        binding.tvAddress.visibility = View.VISIBLE
        binding.tvGender.visibility = View.VISIBLE
        binding.rdGroup.visibility = View.GONE
    }

    private fun move(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun showPopupLogout(){
        @SuppressLint("UserCompatLoadingForDrawables")
        popupLogout = Dialog(this)
        popupLogout?.setContentView(R.layout.popup_ask)
        popupLogout?.setCancelable(true)
        popupLogout?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window : Window = popupLogout?.window!!
        window.setGravity(Gravity.CENTER)
        val tvQuestion = popupLogout?.findViewById<TextView>(R.id.tv_question)
        val btnYes = popupLogout?.findViewById<Button>(R.id.btn_yes)
        val btnNo = popupLogout?.findViewById<Button>(R.id.btn_no)
        tvQuestion?.text = getString(R.string.ask_logout)
        btnYes?.setOnClickListener { presenter.logoutSales() }
        btnNo?.setOnClickListener { popupLogout?.dismiss() }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadingUpload() {
        popupLoading = Dialog(this)
        popupLoading?.setContentView(R.layout.popup_loading)
        popupLoading?.setCancelable(false)
        popupLoading?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window: Window = popupLoading?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageLogout(msg: String) {
        Log.d("logout", msg)
        popupLogout?.dismiss()
        if (msg.contains("Success")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            move()
        }else{
            Snackbar.make(binding.tvActionLogout, "Logout Failed.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun messageUpdateProfile(msg: String) {
        Log.d("updateProfile", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        popupLoading?.dismiss()
        if (msg.contains("Success")){
            stateSave()
            onBackPressed()
        }
    }

    override fun messageUploadImage(msg: String) {
        Log.d("uploadPhotoProfile", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        popupLoading?.dismiss()
    }
}
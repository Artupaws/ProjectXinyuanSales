package project.xinyuan.sales.view.addfragment.addcustomerphoto

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.createFormData
import okhttp3.RequestBody
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAddPhotoCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import java.io.File


class AddPhotoCustomerActivity : AppCompatActivity(), View.OnClickListener, AddPhotoCustomerContract {

    private lateinit var binding : ActivityAddPhotoCustomerBinding
    private lateinit var presenter: AddPhotoCustomerPresenter
    private var idArea:Int=0
    private var companyName:String=""
    private var companyAddress:String=""
    private var nameAdmin:String=""
    private var idCardAdmin:String=""
    private var phoneAdmin:String=""
    private var companyPhone:String=""
    private var companyNpwp:String=""
    private var addressAdmin:String=""
    private var npwpAdmin:String=""
    private var placeAndBirthAdmin:String=""
    private var fileUri: Uri = Uri.EMPTY
    private var statusCapture:String = ""
    private var filePhotoShopOne: File? =null
    private var bodyPhotoShopTwo: MultipartBody.Part? = null
    private lateinit var bodyPhotoIdCardAdmin: MultipartBody.Part
    private lateinit var bodyPhotoNpwpAdmin: MultipartBody.Part
    private lateinit var bodyPhotoNpwpCompany: MultipartBody.Part
    private var idCustomer:Int = 0
    private var popupLoading: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPhotoCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AddPhotoCustomerPresenter(this, this)
        binding.toolbar.title = "Photo Document"
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.ivShopOne.setOnClickListener(this)
        binding.ivShopTwo.setOnClickListener(this)
        binding.ivIdCard.setOnClickListener(this)
        binding.ivNpwpAdmin.setOnClickListener(this)
        binding.ivNpwpCompany.setOnClickListener(this)
        binding.btnAddCustomer.setOnClickListener(this)

        idArea = intent.getIntExtra("idArea", 0)
        companyName = intent.getStringExtra("companyName")
        companyAddress = intent.getStringExtra("companyAddress")
        nameAdmin = intent.getStringExtra("nameAdmin")
        idCardAdmin = intent.getStringExtra("idCardAdmin")
        phoneAdmin = intent.getStringExtra("phoneAdmin")
        companyPhone = intent.getStringExtra("companyPhone")
        companyNpwp = intent.getStringExtra("companyNpwp")
        addressAdmin = intent.getStringExtra("addressAdmin")
        npwpAdmin = intent.getStringExtra("npwpAdmin")
        placeAndBirthAdmin = intent.getStringExtra("placeAndBirthAdmin")

        Log.d("checkData", "$idArea$companyName$companyAddress$nameAdmin$idCardAdmin" +
                "$phoneAdmin$companyPhone$companyNpwp$addressAdmin$npwpAdmin$placeAndBirthAdmin")
        loadingUpload()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_shop_one -> {
                statusCapture = "shopOne"
                checkPermission()
            }
            R.id.iv_shop_two -> {
                statusCapture = "shopTwo"
                checkPermission()
            }
            R.id.iv_id_card -> {
                statusCapture = "idCardAdmin"
                checkPermission()
            }
            R.id.iv_npwp_admin -> {
                statusCapture = "npwpAdmin"
                checkPermission()
            }
            R.id.iv_npwp_company -> {
                statusCapture = "npwpCompany"
                checkPermission()
            }
            R.id.btn_add_customer -> {
                popupLoading?.show()
                presenter.addDataCustomer(idArea,companyName,companyAddress,nameAdmin,idCardAdmin,phoneAdmin,companyPhone,companyNpwp
                ,addressAdmin,placeAndBirthAdmin,npwpAdmin)
            }
        }
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    ||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    companion object {
        //image pick code
        private val IMAGE_CAPTURE_CODE = 1001

        //Permission code
        private val PERMISSION_CODE = 1000
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (statusCapture) {
                "shopOne" -> {
                    binding.ivShopOne.setImageURI(fileUri)
                    val filePath = getRealPathFromURIPath(fileUri)
                    val file = File(filePath)
                    val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    bodyPhotoShopTwo = createFormData("foto_toko", file.name, mFile)
                }
            }
        }
//                "shopTwo" -> {
//                    binding.ivShopTwo.setImageURI(fileUri)
////                    val filePath = fileUri?.let { getRealPathFromURIPath(it) }
////                    val file = File(filePath)
////                    val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
////                   bodyPhotoShopTwo = createFormData("foto_toko", file.name, mFile)
//                }
//                "idCardAdmin" -> {
//                    binding.ivIdCard.setImageURI(fileUri)
////                    val filePath = fileUri?.let { getRealPathFromURIPath(it) }
////                    val file = File(filePath)
////                    val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
////                    bodyPhotoIdCardAdmin = createFormData("ktp", file.name, mFile)
//                }
//                "npwpAdmin" -> {
//                    binding.ivNpwpAdmin.setImageURI(fileUri)
////                    val filePath = fileUri?.let { getRealPathFromURIPath(it) }
////                    val file = File(filePath)
////                    val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
////                    bodyPhotoNpwpAdmin = createFormData("npwp_pengurus", file.name, mFile)
//                }
//                "npwpCompany" -> {
//                    binding.ivNpwpCompany.setImageURI(fileUri)
////                    val filePath = fileUri?.let { getRealPathFromURIPath(it) }
////                    val file = File(filePath)
////                    val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
////                    bodyPhotoNpwpCompany = createFormData("npwp_perusahaan", file.name, mFile)
//                }
//            }
//        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri): String? {
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun openCamera(){
        val values = ContentValues()
        val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(MediaStore.Images.Media.TITLE, "Picture")
        fileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadingUpload(){
        popupLoading = Dialog(this)
        popupLoading?.setContentView(R.layout.popup_loading)
        popupLoading?.setCancelable(true)
        popupLoading?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window: Window = popupLoading?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageUploadPhoto(msg: String) {
        Log.d("uploadPhoto", msg)
        popupLoading?.dismiss()
    }

    override fun messageRegisterDataCustomer(msg: String) {
        Log.d("registerData", msg)
        if (msg.contains("Success")){
            val idCustomerFill: RequestBody = RequestBody.create(MultipartBody.FORM, "12")
            val filePath = getRealPathFromURIPath(fileUri)
            val file = File(filePath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            bodyPhotoShopTwo = createFormData("foto_toko", file.name, mFile)
            presenter.addPhotoCustomer(idCustomerFill, null, bodyPhotoShopTwo, null, null )
        }
    }

    override fun getIdCustomer(idCustomerFill: DataCustomer?) {
        Log.d("idCustomer", idCustomerFill?.id.toString())
        idCustomer = idCustomerFill?.id!!
    }

}


package project.xinyuan.sales.view.addfragment.addcustomerphoto

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.createFormData
import okhttp3.RequestBody
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAddPhotoCustomerBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.view.dashboard.DashboardActivity
import java.io.ByteArrayOutputStream
import java.io.File


class AddPhotoCustomerActivity : AppCompatActivity(), View.OnClickListener, AddPhotoCustomerContract {

    private lateinit var binding: ActivityAddPhotoCustomerBinding
    private lateinit var presenter: AddPhotoCustomerPresenter
    private var idArea: Int = 0
    private var companyName: String = ""
    private var companyAddress: String = ""
    private var nameAdmin: String = ""
    private var idCardAdmin: String = ""
    private var phoneAdmin: String = ""
    private var companyPhone: String = ""
    private var companyNpwp: String = ""
    private var addressAdmin: String = ""
    private var npwpAdmin: String = ""
    private var placeAndBirthAdmin: String = ""
    private var uriPhotoShop: Uri = Uri.EMPTY
    private var uriPhotoIdCard: Uri = Uri.EMPTY
    private var uriPhotoNpwpAdmin: Uri = Uri.EMPTY
    private var uriNpwpCompany: Uri = Uri.EMPTY
    private var statusCapture: String = ""
    private var filePhotoShopOne: File? = null
    private var bodyPhotoShopTwo: MultipartBody.Part? = null
    private var bodyPhotoIdCardAdmin: MultipartBody.Part? = null
    private var bodyPhotoNpwpAdmin: MultipartBody.Part? = null
    private var bodyPhotoNpwpCompany: MultipartBody.Part? = null
    private var idCustomer: String = ""
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
        when (v?.id) {
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
                presenter.addDataCustomer(idArea, companyName, companyAddress, nameAdmin, idCardAdmin, phoneAdmin, companyPhone, companyNpwp, addressAdmin, placeAndBirthAdmin, npwpAdmin)
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else if (statusCapture == "shopOne") {
                openCamera()
            } else if (statusCapture == "idCardAdmin") {
                openCameraIdCard()
            } else if (statusCapture == "npwpAdmin") {
                openCameraNpwpAdmin()
            } else if (statusCapture == "npwpCompany") {
                openCameraNpwpCompany()
            }
        } else if (statusCapture == "shopOne") {
            openCamera()
        } else if (statusCapture == "idCardAdmin") {
            openCameraIdCard()
        } else if (statusCapture == "npwpAdmin") {
            openCameraNpwpAdmin()
        } else if (statusCapture == "npwpCompany") {
            openCameraNpwpCompany()
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
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when (statusCapture) {
                        "shopOne" -> {
                            openCamera()
                        }
                        "idCardAdmin" -> {
                            openCameraIdCard()
                        }
                        "npwpAdmin" -> {
                            openCameraNpwpAdmin()
                        }
                        "npwpCompany" -> {
                            openCameraNpwpCompany()
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bytes = ByteArrayOutputStream()
        if (resultCode == RESULT_OK) {
            when (statusCapture) {
                "shopOne" -> {
                    binding.ivShopOne.setImageURI(uriPhotoShop)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = Images.Media.getBitmap(contentResolver, uriPhotoShop)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoShopTwo = createFormData("foto_toko", file.name, mFile)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uriPhotoShop)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoShopTwo = createFormData("foto_toko", file.name, mFile)
                    }
                }

                "idCardAdmin" -> {
                    binding.ivIdCard.setImageURI(uriPhotoIdCard)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = Images.Media.getBitmap(contentResolver, uriPhotoIdCard)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
//                    val filePath = getRealPathFromURIPath(uriPhotoIdCard)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoIdCardAdmin = createFormData("ktp", file.name, mFile)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uriPhotoIdCard)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
//                    val filePath = getRealPathFromURIPath(uriPhotoIdCard)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoIdCardAdmin = createFormData("ktp", file.name, mFile)
                    }
                }

                "npwpAdmin" -> {
                    binding.ivNpwpAdmin.setImageURI(uriPhotoNpwpAdmin)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = Images.Media.getBitmap(contentResolver, uriPhotoNpwpAdmin)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoNpwpAdmin = createFormData("npwp_pengurus", file.name, mFile)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uriPhotoNpwpAdmin)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoNpwpAdmin = createFormData("npwp_pengurus", file.name, mFile)
                    }
                }

                "npwpCompany" -> {
                    binding.ivNpwpCompany.setImageURI(uriNpwpCompany)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = Images.Media.getBitmap(contentResolver, uriNpwpCompany)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoNpwpCompany = createFormData("npwp_perusahaan", file.name, mFile)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uriNpwpCompany)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                        val path = Images.Media.insertImage(contentResolver, bitmap, "Xinyuan-Image", null)
                        val filePath = getRealPathFromURIPath(Uri.parse(path))
                        val file = File(filePath)
                        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        bodyPhotoNpwpCompany = createFormData("npwp_perusahaan", file.name, mFile)
                    }
                }
            }
        }
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

    private fun openCamera() {
        val values = ContentValues()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(Images.Media.TITLE, "Shop")
        uriPhotoShop = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoShop)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun openCameraIdCard() {
        val values = ContentValues()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(Images.Media.TITLE, "IDCard")
        uriPhotoIdCard = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoIdCard)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun openCameraNpwpAdmin() {
        val values = ContentValues()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(Images.Media.TITLE, "Npwp-Admin")
        uriPhotoNpwpAdmin = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoNpwpAdmin)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun openCameraNpwpCompany() {
        val values = ContentValues()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(Images.Media.TITLE, "Npwp-Company")
        uriNpwpCompany = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriNpwpCompany)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun move() {
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadingUpload() {
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
        if (msg.contains("Success")) {
            Snackbar.make(binding.btnAddCustomer, "Add Customer Success", Snackbar.LENGTH_SHORT).show()
            move()
        } else {
            Snackbar.make(binding.btnAddCustomer, "Add Customer Failed", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun messageRegisterDataCustomer(msg: String) {
        Log.d("registerData", msg)
    }

    override fun getIdCustomer(item: DataCustomer?) {
        idCustomer = item?.id.toString()
        Log.d("idCustomer", idCustomer)
        if (idCustomer.isNotEmpty()) {
            popupLoading?.show()
            val idCustomerFill: RequestBody = RequestBody.create(MultipartBody.FORM, idCustomer)
            presenter.addPhotoCustomer(idCustomerFill, bodyPhotoIdCardAdmin, bodyPhotoShopTwo, bodyPhotoNpwpAdmin, bodyPhotoNpwpCompany)
        }
    }

}


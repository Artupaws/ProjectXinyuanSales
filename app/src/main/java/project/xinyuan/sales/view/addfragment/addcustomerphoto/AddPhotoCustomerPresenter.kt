package project.xinyuan.sales.view.addfragment.addcustomerphoto

import android.content.Context
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.customer.ResponseRegisterDataCustomer
import project.xinyuan.sales.model.customer.ResponseUploadImageCustomer
import retrofit2.Call
import retrofit2.Response

class AddPhotoCustomerPresenter(val view:AddPhotoCustomerContract, val context: Context) {

    fun addDataCustomer(idArea:Int, companyName:String,companyAddress:String,administratorName:String,administratorId:String,administratorPhone:String,
                        companyPhone:String,companyNpwp:String,administratorAddress:String,administratorBirthdate:String,administratorNpwp:String, idLevel:Int){
        val addDataCustomer = NetworkConfig().getConnectionXinyuanBearer(context).registerDataCustomer(idArea, companyName, companyAddress, administratorName, administratorId, administratorPhone,
            companyPhone, companyNpwp, administratorAddress, administratorBirthdate, administratorNpwp, idLevel)
        addDataCustomer.enqueue(object :retrofit2.Callback<ResponseRegisterDataCustomer>{
            override fun onResponse(call: Call<ResponseRegisterDataCustomer>, response: Response<ResponseRegisterDataCustomer>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataCustomer
                    view.messageRegisterDataCustomer(response.body()?.message.toString())
                    view.getIdCustomer(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageRegisterDataCustomer(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseRegisterDataCustomer>, t: Throwable) {
                view.messageRegisterDataCustomer(t.localizedMessage.toString())
            }

        })

    }

    fun addPhotoCustomer(id_customer:RequestBody, ktp: MultipartBody.Part?, shopImage:MultipartBody.Part?, npwpAdmin: MultipartBody.Part?, npwpCompany:MultipartBody.Part?){
        val addPhotoCustomer = NetworkConfig().getConnectionXinyuanBearer(context).uploadImageCustomer(id_customer, ktp, shopImage, npwpAdmin, npwpCompany)
        addPhotoCustomer.enqueue(object : retrofit2.Callback<ResponseUploadImageCustomer>{
            override fun onResponse(call: Call<ResponseUploadImageCustomer>, response: Response<ResponseUploadImageCustomer>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUploadPhoto(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageUploadPhoto(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUploadImageCustomer>, t: Throwable) {
                view.messageUploadPhoto(t.localizedMessage.toString())
            }

        })

    }

}
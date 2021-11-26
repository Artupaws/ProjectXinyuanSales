package project.xinyuan.sales.view.account

import android.content.Context
import okhttp3.MultipartBody
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.sales.ResponseLogout
import project.xinyuan.sales.model.sales.ResponseUpdateProfileSales
import retrofit2.Call
import retrofit2.Response

class AccountActivityPresenter(val view:AccountActivityContract, val context: Context) {

    fun logoutSales(){
        val logoutSales = NetworkConfig().getConnectionXinyuanBearer(context).logoutSales()
        logoutSales.enqueue(object : retrofit2.Callback<ResponseLogout>{
            override fun onResponse(call: Call<ResponseLogout>, response: Response<ResponseLogout>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageLogout(response.body()?.message.toString())
                }else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageLogout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                view.messageLogout(t.localizedMessage.toString())
            }

        })
    }

    fun updateProfile(name:String, email:String, phone:String, address:String, gender:String, idArea:Int){
        val updateProfile = NetworkConfig().getConnectionXinyuanBearer(context).updateProfileSales(name, email, phone, address, gender, idArea)
        updateProfile.enqueue(object : retrofit2.Callback<ResponseUpdateProfileSales>{
            override fun onResponse(call: Call<ResponseUpdateProfileSales>, response: Response<ResponseUpdateProfileSales>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateProfile(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageUpdateProfile(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUpdateProfileSales>, t: Throwable) {
                view.messageUpdateProfile(t.localizedMessage.toString())
            }

        })
    }

    fun updatePhotoProfile(photo:MultipartBody.Part){
        val updatePhotoProfile = NetworkConfig().getConnectionXinyuanBearer(context).updatePhotoSales(photo)
        updatePhotoProfile.enqueue(object : retrofit2.Callback<ResponseUpdateProfileSales>{
            override fun onResponse(call: Call<ResponseUpdateProfileSales>, response: Response<ResponseUpdateProfileSales>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUploadImage(response.body()?.message.toString())
                }else{
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageUploadImage(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUpdateProfileSales>, t: Throwable) {
                view.messageUploadImage(t.localizedMessage.toString())
            }

        })
    }

}
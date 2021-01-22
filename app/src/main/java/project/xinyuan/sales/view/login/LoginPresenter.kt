package project.xinyuan.sales.view.login

import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseLogin
import retrofit2.Call
import retrofit2.Response

class LoginPresenter(val view:LoginContract) {

    fun loginSales(deviceName:String, email:String, password:String){
        val loginSales = NetworkConfig().getConnectionXinyuan().loginSales(deviceName, email, password)
        loginSales.enqueue(object : retrofit2.Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageLogin(response.body()?.message.toString())
                    view.getTokenLogin(response.body()?.dataLogin?.token.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageLogin(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                view.messageLogin(t.localizedMessage.toString())
            }

        })
    }

}
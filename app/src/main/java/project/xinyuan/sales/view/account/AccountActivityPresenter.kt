package project.xinyuan.sales.view.account

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseLogout
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

}
package project.xinyuan.sales.view.dashboard

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseGetDetailSales
import project.xinyuan.sales.model.ResponseLogout
import retrofit2.Call
import retrofit2.Response

class DashboardPresenter(val view: DashboardContract, val context: Context) {

    fun getDetailSales(){
        val getDetailSales = NetworkConfig().getConnectionXinyuanBearer(context).getDetailSales()
        getDetailSales.enqueue(object : retrofit2.Callback<ResponseGetDetailSales>{
            override fun onResponse(call: Call<ResponseGetDetailSales>, response: Response<ResponseGetDetailSales>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataSales
                    view.getDetailSales(data)
                    view.messageGetDetailSales(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetDetailSales(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetDetailSales>, t: Throwable) {
                view.messageGetDetailSales(t.localizedMessage.toString())
            }

        })
    }

    fun logoutSales(){
        val logoutSales = NetworkConfig().getConnectionXinyuanBearer(context).logoutSales()
        logoutSales.enqueue(object : retrofit2.Callback<ResponseLogout>{
            override fun onResponse(call: Call<ResponseLogout>, response: Response<ResponseLogout>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageLogoutSales(response.body()?.message.toString())
                }else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageLogoutSales(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                view.messageLogoutSales(t.localizedMessage.toString())
            }

        })
    }

}
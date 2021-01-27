package project.xinyuan.sales.view.home.customer

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseGetListCustomer
import retrofit2.Call
import retrofit2.Response

class CustomerPresenter(val view: CustomerContract, val context: Context) {

    fun getListCustomer(){
        val getListCustomer = NetworkConfig().getConnectionXinyuanBearer(context).getListCustomer()
        getListCustomer.enqueue(object : retrofit2.Callback<ResponseGetListCustomer>{
            override fun onResponse(call: Call<ResponseGetListCustomer>, response: Response<ResponseGetListCustomer>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListCustomer(data)
                    view.messageGetListCustomer(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListCustomer(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListCustomer>, t: Throwable) {
                view.messageGetListCustomer(t.localizedMessage.toString())
            }

        })
    }

}
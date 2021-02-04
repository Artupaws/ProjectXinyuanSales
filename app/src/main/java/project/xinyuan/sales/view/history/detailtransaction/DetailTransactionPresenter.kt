package project.xinyuan.sales.view.history.detailtransaction

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseMakePayment
import retrofit2.Call
import retrofit2.Response

class DetailTransactionPresenter(val view:DetailTransactionContract, val context: Context) {

    fun makePaymentCustomer(idTransaction:Int, paid:Int){
        val makePaymentCustomer = NetworkConfig().getConnectionXinyuanBearer(context).makePaymentCustomer(idTransaction, paid)
        makePaymentCustomer.enqueue(object : retrofit2.Callback<ResponseMakePayment>{
            override fun onResponse(call: Call<ResponseMakePayment>, response: Response<ResponseMakePayment>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataPayment
                    view.getDataPayment(data)
                    view.messageMakePayment(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageMakePayment(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseMakePayment>, t: Throwable) {
                view.messageMakePayment(t.localizedMessage.toString())
            }

        })
    }

}
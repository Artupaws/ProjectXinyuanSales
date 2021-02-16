package project.xinyuan.sales.view.history.detailtransaction

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseGetPaymentAccounts
import project.xinyuan.sales.model.ResponseMakePayment
import retrofit2.Call
import retrofit2.Response

class DetailTransactionPresenter(val view:DetailTransactionContract, val context: Context) {

    fun makePaymentCustomer(idTransaction:Int, paid:Int, idPaymentAccount:Int){
        val makePaymentCustomer = NetworkConfig().getConnectionXinyuanBearer(context).makePaymentCustomer(idTransaction, paid, idPaymentAccount)
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

    fun getPaymentAccount(){
        val getPaymentAccount = NetworkConfig().getConnectionXinyuanBearer(context).getPaymentAccount()
        getPaymentAccount.enqueue(object : retrofit2.Callback<ResponseGetPaymentAccounts>{
            override fun onResponse(call: Call<ResponseGetPaymentAccounts>, response: Response<ResponseGetPaymentAccounts>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getPaymentAccount(data)
                    view.messageGetPaymentAccount(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetPaymentAccount(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetPaymentAccounts>, t: Throwable) {
                view.messageGetPaymentAccount(t.localizedMessage.toString())
            }

        })
    }

}
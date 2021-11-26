package project.xinyuan.sales.view.history

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.transaction.ResponseGetTransactionDetail
import retrofit2.Call
import retrofit2.Response

class HistoryTransactionPresenter(val view: HistoryTransactionContract, val context: Context){

    fun getTransactionDetail(){
        val getTransactionDetail = NetworkConfig().getConnectionXinyuanBearer(context).getTransactionList()
        getTransactionDetail.enqueue(object : retrofit2.Callback<ResponseGetTransactionDetail>{
            override fun onResponse(call: Call<ResponseGetTransactionDetail>, response: Response<ResponseGetTransactionDetail>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataTransaction
                    view.getDataTransaction(data)
                    view.messageGetTransactionDetail(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetTransactionDetail(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetTransactionDetail>, t: Throwable) {
                view.messageGetTransactionDetail(t.localizedMessage.toString())
            }

        })
    }

}
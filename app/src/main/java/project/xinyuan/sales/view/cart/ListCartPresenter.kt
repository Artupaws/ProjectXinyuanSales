package project.xinyuan.sales.view.cart

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseAddTransaction
import retrofit2.Call
import retrofit2.Response

class ListCartPresenter(val view:ListCartContract, val context: Context) {

    fun addDataFormalTransaction(invoiceNumber:String, idCustomer:Int, payment:String, paymentPeriod:String, paid:String, totalPayment:String){
        val addDataFormalTransaction = NetworkConfig().getConnectionXinyuanBearer(context).addDataFormalTransaction(invoiceNumber, idCustomer, payment, paymentPeriod, paid, totalPayment)
        addDataFormalTransaction.enqueue(object : retrofit2.Callback<ResponseAddTransaction>{
            override fun onResponse(call: Call<ResponseAddTransaction>, response: Response<ResponseAddTransaction>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataFormalTransaction
                    view.getDataFormalTransaction(data)
                    view.messageAddDataFormalTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageAddDataFormalTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddTransaction>, t: Throwable) {
                view.messageAddDataFormalTransaction(t.localizedMessage.toString())
            }

        })
    }

}
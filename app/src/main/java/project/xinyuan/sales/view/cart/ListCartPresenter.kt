package project.xinyuan.sales.view.cart

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseAddProductTransaction
import project.xinyuan.sales.model.ResponseAddTransaction
import project.xinyuan.sales.roomdatabase.CartItem
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

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

    fun addProductTransaction(idTransaction: Int, idProduct:Int, quantity:Int, price:Int, total:Int){
        val addProductTransaction = NetworkConfig().getConnectionXinyuanBearer(context).addProductTransaction(idTransaction, idProduct, quantity, price, total)
        addProductTransaction.enqueue(object :retrofit2.Callback<ResponseAddProductTransaction>{
            override fun onResponse(call: Call<ResponseAddProductTransaction>, response: Response<ResponseAddProductTransaction>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddProductTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageAddProductTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddProductTransaction>, t: Throwable) {
                view.messageAddProductTransaction(t.localizedMessage.toString())
            }

        })

    }

}
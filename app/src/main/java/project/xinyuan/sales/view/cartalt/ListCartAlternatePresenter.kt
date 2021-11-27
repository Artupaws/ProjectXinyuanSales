package project.xinyuan.sales.view.cartalt

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.company.ResponseGetCompanyList
import project.xinyuan.sales.model.transaction.ResponseAddProductTransaction
import project.xinyuan.sales.model.transaction.ResponseAddTransaction
import project.xinyuan.sales.model.paymentaccount.ResponseGetPaymentAccounts
import project.xinyuan.sales.model.transaction.RequestCreateTransaction
import project.xinyuan.sales.model.warehouse.ResponseGetWarehouseList
import retrofit2.Call
import retrofit2.Response

class ListCartAlternatePresenter(val view:ListCartAlternateContract, val context: Context) {

    fun addDataFormalTransaction(invoiceNumber:String, idCustomer:Int, payment:String, paymentPeriod:Int, paid:Int, totalPayment:Int, idPaymentAccount:Int, dateTransaction:String){
        val addDataFormalTransaction = NetworkConfig().getConnectionXinyuanBearer(context).addDataFormalTransaction(invoiceNumber, idCustomer, payment, paymentPeriod, paid, totalPayment, idPaymentAccount, dateTransaction)
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

    fun addTransaction(request: RequestCreateTransaction){
        val addDataFormalTransaction = NetworkConfig().getConnectionXinyuanBearer(context).addTransaction(request)
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

    fun getWarehouse(){
        val getPaymentAccount = NetworkConfig().getConnectionXinyuanBearer(context).getWarehouseList()
        getPaymentAccount.enqueue(object : retrofit2.Callback<ResponseGetWarehouseList>{
            override fun onResponse(call: Call<ResponseGetWarehouseList>, response: Response<ResponseGetWarehouseList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getWarehouse(data)
                    view.messageGetPaymentAccount(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetPaymentAccount(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetWarehouseList>, t: Throwable) {
                view.messageGetPaymentAccount(t.localizedMessage.toString())
            }

        })
    }

    fun getCompany(){
        val getPaymentAccount = NetworkConfig().getConnectionXinyuanBearer(context).getCompanyList()
        getPaymentAccount.enqueue(object : retrofit2.Callback<ResponseGetCompanyList>{
            override fun onResponse(call: Call<ResponseGetCompanyList>, response: Response<ResponseGetCompanyList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getCompany(data)
                    view.messageGetPaymentAccount(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetPaymentAccount(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCompanyList>, t: Throwable) {
                view.messageGetPaymentAccount(t.localizedMessage.toString())
            }

        })
    }

}
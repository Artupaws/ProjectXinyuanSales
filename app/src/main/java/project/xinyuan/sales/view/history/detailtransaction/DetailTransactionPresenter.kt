package project.xinyuan.sales.view.history.detailtransaction

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.giro.ResponseAddTransactionGiro
import project.xinyuan.sales.model.bank.ResponseGetListBank
import project.xinyuan.sales.model.paymentaccount.ResponseGetPaymentAccounts
import project.xinyuan.sales.model.payment.ResponseMakePayment
import retrofit2.Call
import retrofit2.Response

class DetailTransactionPresenter(val view:DetailTransactionContract, val context: Context) {

    fun makePaymentCustomer(idTransaction:Int, paid:Int, idPaymentAccount:Int, datePayment:String){
        val makePaymentCustomer = NetworkConfig().getConnectionXinyuanBearer(context).makePaymentCustomer(idTransaction, paid, idPaymentAccount,datePayment)
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

    fun getBankName(){
        val getBankName = NetworkConfig().getConnectionXinyuanBearer(context).getListBank()
        getBankName.enqueue(object : retrofit2.Callback<ResponseGetListBank>{
            override fun onResponse(call: Call<ResponseGetListBank>, response: Response<ResponseGetListBank>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetBankName(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getBankName(data)
                }else {
                    val error = JSONObject(response.message().toString())
                    view.messageGetBankName(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListBank>, t: Throwable) {
                view.messageGetBankName(t.localizedMessage.toString())
            }

        })
    }

    fun addTransactionGiro(idTransaction:Int, idBank:Int, giroNumber:String, balance:Long, dateReceived:String){
        val addTransactionGiro = NetworkConfig().getConnectionXinyuanBearer(context).addTransactionGiro(idTransaction, idBank, giroNumber, balance, dateReceived)
        addTransactionGiro.enqueue(object : retrofit2.Callback<ResponseAddTransactionGiro>{
            override fun onResponse(call: Call<ResponseAddTransactionGiro>, response: Response<ResponseAddTransactionGiro>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddTransactionGiro(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageAddTransactionGiro(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddTransactionGiro>, t: Throwable) {
                view.messageAddTransactionGiro(t.localizedMessage.toString())
            }

        })

    }

}
package project.xinyuan.sales.view.history.detailtransaction

import project.xinyuan.sales.model.DataBank
import project.xinyuan.sales.model.DataPayment
import project.xinyuan.sales.model.DataPaymentAccount

interface DetailTransactionContract {

    fun messageGetPaymentAccount(msg:String)
    fun messageMakePayment(msg:String)
    fun messageGetBankName(msg:String)
    fun messageAddTransactionGiro(msg: String)
    fun getDataPayment(data:DataPayment?)
    fun getPaymentAccount(data:List<DataPaymentAccount?>?)
    fun getBankName(data:MutableList<DataBank?>?)
}
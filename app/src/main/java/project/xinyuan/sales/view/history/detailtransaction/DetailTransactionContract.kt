package project.xinyuan.sales.view.history.detailtransaction

import project.xinyuan.sales.model.bank.master.DataBank
import project.xinyuan.sales.model.payment.master.DataPayment
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount

interface DetailTransactionContract {

    fun messageGetPaymentAccount(msg:String)
    fun messageMakePayment(msg:String)
    fun messageGetBankName(msg:String)
    fun messageAddTransactionGiro(msg: String)
    fun getDataPayment(data: DataPayment?)
    fun getPaymentAccount(data:List<DataPaymentAccount?>?)
    fun getBankName(data:MutableList<DataBank?>?)
}
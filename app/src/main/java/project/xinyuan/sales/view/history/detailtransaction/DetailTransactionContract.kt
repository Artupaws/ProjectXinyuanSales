package project.xinyuan.sales.view.history.detailtransaction

import project.xinyuan.sales.model.DataPayment

interface DetailTransactionContract {

    fun messageMakePayment(msg:String)
    fun getDataPayment(data:DataPayment?)
}
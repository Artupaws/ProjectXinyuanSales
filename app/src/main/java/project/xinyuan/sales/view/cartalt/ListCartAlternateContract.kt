package project.xinyuan.sales.view.cartalt

import project.xinyuan.sales.model.transaction.master.DataFormalTransaction
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount

interface ListCartAlternateContract {

    fun messageAddProductTransaction(msg:String)
    fun messageGetPaymentAccount(msg:String)
    fun messageAddDataFormalTransaction(msg:String)
    fun getDataFormalTransaction(data: DataFormalTransaction?)
    fun getPaymentAccount(data:List<DataPaymentAccount?>?)

}
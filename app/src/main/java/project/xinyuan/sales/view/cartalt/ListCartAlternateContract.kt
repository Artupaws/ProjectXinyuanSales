package project.xinyuan.sales.view.cartalt

import project.xinyuan.sales.model.company.master.CompanyItem
import project.xinyuan.sales.model.transaction.master.DataFormalTransaction
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount
import project.xinyuan.sales.model.warehouse.master.DataWarehouse

interface ListCartAlternateContract {

    fun messageAddProductTransaction(msg:String)
    fun messageGetPaymentAccount(msg:String)
    fun messageAddDataFormalTransaction(msg:String)
    fun getDataFormalTransaction(data: DataFormalTransaction?)
    fun getPaymentAccount(data:List<DataPaymentAccount?>?)
    fun getWarehouse(data:List<DataWarehouse?>?)
    fun getCompany(data:List<CompanyItem?>?)

}
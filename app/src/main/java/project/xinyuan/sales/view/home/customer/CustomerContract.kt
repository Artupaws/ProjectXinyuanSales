package project.xinyuan.sales.view.home.customer

import project.xinyuan.sales.model.customer.master.DataCustomer

interface CustomerContract {

    fun messageGetListCustomer(msg:String)
    fun getListCustomer(data:List<DataCustomer?>?)

}
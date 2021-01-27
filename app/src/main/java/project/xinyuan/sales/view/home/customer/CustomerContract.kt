package project.xinyuan.sales.view.home.customer

import project.xinyuan.sales.model.DataCustomer

interface CustomerContract {

    fun messageGetListCustomer(msg:String)
    fun getListCustomer(data:List<DataCustomer?>?)

}
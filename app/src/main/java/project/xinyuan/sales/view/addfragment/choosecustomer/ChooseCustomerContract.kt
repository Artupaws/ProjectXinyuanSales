package project.xinyuan.sales.view.addfragment.choosecustomer

import project.xinyuan.sales.model.customer.master.DataCustomer

interface ChooseCustomerContract {

    fun messageGetListCustomer(msg:String)
    fun getListCustomer(data:List<DataCustomer?>?)

}
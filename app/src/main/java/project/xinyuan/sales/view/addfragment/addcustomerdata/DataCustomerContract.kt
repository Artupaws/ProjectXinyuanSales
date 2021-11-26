package project.xinyuan.sales.view.addfragment.addcustomerdata

import project.xinyuan.sales.model.customer.master.CustomerLevel
import project.xinyuan.sales.model.area.master.DataArea
import project.xinyuan.sales.model.customer.master.DataCustomer

interface DataCustomerContract {

    fun messageCheckIdCustomer(msg:String)
    fun messageRegisterDataCustomer(msg:String)
    fun messageGetListArea(msg:String)
    fun messageGetCustomerLevel(msg:String)
    fun getIdCustomer(idCustomer: DataCustomer?)
    fun getListArea(item:List<DataArea?>?)
    fun getCustomerLevel(dataLevel:MutableList<CustomerLevel?>?)

}
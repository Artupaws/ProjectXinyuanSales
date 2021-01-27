package project.xinyuan.sales.view.addfragment.addcustomerdata

import project.xinyuan.sales.model.DataArea
import project.xinyuan.sales.model.DataCustomer

interface DataCustomerContract {

    fun messageCheckIdCustomer(msg:String)
    fun messageRegisterDataCustomer(msg:String)
    fun messageGetListArea(msg:String)
    fun getIdCustomer(idCustomer:DataCustomer?)
    fun getListArea(item:List<DataArea?>?)

}
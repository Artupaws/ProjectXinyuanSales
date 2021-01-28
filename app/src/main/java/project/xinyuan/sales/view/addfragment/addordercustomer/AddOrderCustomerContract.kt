package project.xinyuan.sales.view.addfragment.addordercustomer

import project.xinyuan.sales.model.DataProduct

interface AddOrderCustomerContract {

    fun messageGetListProduct(msg:String)
    fun getListProduct(data:List<DataProduct?>?)

}
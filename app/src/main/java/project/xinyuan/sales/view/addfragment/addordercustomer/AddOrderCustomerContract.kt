package project.xinyuan.sales.view.addfragment.addordercustomer

import project.xinyuan.sales.model.product.master.DataProduct

interface AddOrderCustomerContract {

    fun messageGetListProduct(msg:String)
    fun getListProduct(data:List<DataProduct?>?)

}
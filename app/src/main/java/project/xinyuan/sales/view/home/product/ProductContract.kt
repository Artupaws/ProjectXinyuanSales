package project.xinyuan.sales.view.home.product

import project.xinyuan.sales.model.DataProduct

interface ProductContract {

    fun messageGetListProduct(msg:String)
    fun getDataListProduct(item:List<DataProduct?>?)

}
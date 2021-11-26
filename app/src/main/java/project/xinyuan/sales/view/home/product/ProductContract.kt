package project.xinyuan.sales.view.home.product

import project.xinyuan.sales.model.product.master.DataProduct

interface ProductContract {

    fun messageGetListProduct(code:Int,msg:String)
    fun getDataListProduct(item:List<DataProduct?>?)

}
package project.xinyuan.sales.view.cart

import project.xinyuan.sales.model.DataFormalTransaction

interface ListCartContract {

    fun messageAddDataFormalTransaction(msg:String)
    fun getDataFormalTransaction(data:DataFormalTransaction?)

}
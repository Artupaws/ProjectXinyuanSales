package project.xinyuan.sales.view.history

import project.xinyuan.sales.model.DataTransaction

interface HistoryTransactionContract {

    fun messageGetTransactionDetail(msg:String)
    fun getDataTransaction(data:List<DataTransaction?>?)

}
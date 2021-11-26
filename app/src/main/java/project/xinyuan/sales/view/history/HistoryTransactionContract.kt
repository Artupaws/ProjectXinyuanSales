package project.xinyuan.sales.view.history

import project.xinyuan.sales.model.transaction.master.DataTransaction

interface HistoryTransactionContract {

    fun messageGetTransactionDetail(msg:String)
    fun getDataTransaction(data:List<DataTransaction?>?)

}
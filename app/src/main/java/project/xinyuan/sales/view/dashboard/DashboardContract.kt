package project.xinyuan.sales.view.dashboard

import project.xinyuan.sales.model.sales.master.DataSales

interface DashboardContract {

    fun messageGetDetailSales(msg:String)
    fun messageLogoutSales(msg:String)
    fun getDetailSales(data: DataSales?)

}
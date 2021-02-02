package project.xinyuan.sales.view.dashboard

import project.xinyuan.sales.model.DataSales

interface DashboardContract {

    fun messageGetDetailSales(msg:String)
    fun getDetailSales(data:DataSales?)


}
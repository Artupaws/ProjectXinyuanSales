package project.xinyuan.sales.view.addfragment.addcustomerdata

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.ResponseCheckIdCustomer
import project.xinyuan.sales.model.ResponseGetListArea
import project.xinyuan.sales.model.ResponseRegisterDataCustomer
import retrofit2.Call
import retrofit2.Response

class DataCustomerPresenter(val view:DataCustomerContract, val context: Context) {

    fun getListDataArea(){
        val getListDataArea = NetworkConfig().getConnectionXinyuanBearer(context).getListArea()
        getListDataArea.enqueue(object : retrofit2.Callback<ResponseGetListArea>{
            override fun onResponse(call: Call<ResponseGetListArea>, response: Response<ResponseGetListArea>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListArea(data)
                    view.messageGetListArea(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListArea(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListArea>, t: Throwable) {
                view.messageGetListArea(t.localizedMessage.toString())
            }

        })
    }

    fun checkIdCustomer(idCustomer:String){
        val checkIdCustomer = NetworkConfig().getConnectionXinyuanBearer(context).checkIdCustomer(idCustomer)
        checkIdCustomer.enqueue(object : retrofit2.Callback<ResponseCheckIdCustomer>{
            override fun onResponse(call: Call<ResponseCheckIdCustomer>, response: Response<ResponseCheckIdCustomer>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCheckIdCustomer(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCheckIdCustomer(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCheckIdCustomer>, t: Throwable) {
                view.messageCheckIdCustomer(t.localizedMessage.toString())
            }

        })
    }

    fun addDataCustomer(idArea:Int, companyName:String,companyAddress:String,administratorName:String,administratorId:String,administratorPhone:String,
                        companyPhone:String,companyNpwp:String,administratorAddress:String,administratorBirthdate:String,administratorNpwp:String){
        val addDataCustomer = NetworkConfig().getConnectionXinyuanBearer(context).registerDataCustomer(idArea, companyName, companyAddress, administratorName, administratorId, administratorPhone,
            companyPhone, companyNpwp, administratorAddress, administratorBirthdate, administratorNpwp)
        addDataCustomer.enqueue(object :retrofit2.Callback<ResponseRegisterDataCustomer>{
            override fun onResponse(call: Call<ResponseRegisterDataCustomer>, response: Response<ResponseRegisterDataCustomer>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataCustomer
                    view.messageRegisterDataCustomer(response.body()?.message.toString())
                    view.getIdCustomer(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageRegisterDataCustomer(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseRegisterDataCustomer>, t: Throwable) {
                view.messageRegisterDataCustomer(t.localizedMessage.toString())
            }

        })

    }

}
package project.xinyuan.sales.view.addfragment.addordercustomer

import android.content.Context
import org.json.JSONObject
import project.xinyuan.sales.api.NetworkConfig
import project.xinyuan.sales.model.product.ResponseGetListProduct
import retrofit2.Call
import retrofit2.Response

class AddOrderCustomerPresenter(val view:AddOrderCustomerContract, val context:Context) {

    fun getListProduct(){
        val getListProduct = NetworkConfig().getConnectionXinyuanBearer(context).getListProduct()
        getListProduct.enqueue(object :retrofit2.Callback<ResponseGetListProduct>{
            override fun onResponse(call: Call<ResponseGetListProduct>, response: Response<ResponseGetListProduct>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListProduct(data)
                    view.messageGetListProduct(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListProduct(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListProduct>, t: Throwable) {
                view.messageGetListProduct(t.localizedMessage.toString())
            }

        })
    }

}
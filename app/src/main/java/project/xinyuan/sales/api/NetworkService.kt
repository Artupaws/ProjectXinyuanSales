package project.xinyuan.sales.api

import project.xinyuan.sales.model.ResponseGetListProduct
import project.xinyuan.sales.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService {

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/sales/login")
    fun loginSales(
            @Field("device_name")deviceName:String,
            @Field("email")email:String,
            @Field("password")password:String
    ):Call<ResponseLogin>

    @Headers("No-Authentication: true")
    @POST("api/product")
    fun getListProduct():Call<ResponseGetListProduct>
}
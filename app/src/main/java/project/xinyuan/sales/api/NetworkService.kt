package project.xinyuan.sales.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import project.xinyuan.sales.model.*
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    //Login
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/sales/login")
    fun loginSales(
            @Field("device_name")deviceName:String,
            @Field("email")email:String,
            @Field("password")password:String
    ):Call<ResponseLogin>

    //Get List Product
    @Headers("No-Authentication: true")
    @POST("api/product")
    fun getListProduct():Call<ResponseGetListProduct>

    //Get List Area
    @Headers("No-Authentication: true")
    @POST("api/area")
    fun getListArea():Call<ResponseGetListArea>

    //Register Data Customer
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/customer/register")
    fun registerDataCustomer(
        @Field("id_area")idArea:Int,
        @Field("company_name")companyName:String,
        @Field("company_address")companyAddress:String,
        @Field("administrator_name")administratorName:String,
        @Field("administrator_id")administratorId:String,
        @Field("administrator_phone")administratorPhone:String,
        @Field("company_phone")companyPhone:String,
        @Field("company_npwp")companyNpwp:String,
        @Field("administrator_address")administratorAddress:String,
        @Field("administrator_birthdate")administratorBirthdate:String,
        @Field("administrator_npwp")administratorNpwp:String
    ):Call<ResponseRegisterDataCustomer>

    //Check ID Customer
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/customer/idcheck")
    fun checkIdCustomer(
        @Field("administrator_id")administratorId:String
    ):Call<ResponseCheckIdCustomer>

    //Upload Image Customer
    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/customer/photo")
    fun uploadImageCustomer(
        @Part ("id_customer")idCustomer:RequestBody,
        @Part ktp:MultipartBody.Part?,
        @Part fotoToko:MultipartBody.Part?,
        @Part npwpPengurus:MultipartBody.Part?,
        @Part npwpPerusahaan:MultipartBody.Part?
    ):Call<ResponseUploadImageCustomer>

    //Get Customer List
    @Headers("No-Authentication: true")
    @POST("api/customer")
    fun getListCustomer():Call<ResponseGetListCustomer>

    //Get Detail Sales
    @Headers("No-Authentication: true")
    @POST("api/sales/detail")
    fun getDetailSales():Call<ResponseGetDetailSales>

    //Add Data Formal Transaction
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/transaction")
    fun addDataFormalTransaction(
            @Field("invoice_number")invoiceNumber:String,
            @Field("id_customer")idCustomer:Int,
            @Field("payment")payment:String,
            @Field("payment_period")paymentPeriod:String,
            @Field("paid")paid:String,
            @Field("total_payment")totalPayment:String
    ):Call<ResponseAddTransaction>

    //Add Product Transaction
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/transaction/details")
    fun addProductTransaction(
            @Field("id_transaction")idTransaction:Int,
            @Field("id_product")idPorduct:Int,
            @Field("quantity")quantity:Int,
            @Field("price")price:Int,
            @Field("total")total:Int,
    ):Call<ResponseAddProductTransaction>
}
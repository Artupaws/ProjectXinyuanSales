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
        @Field("administrator_npwp")administratorNpwp:String,
        @Field("id_level")idLevel:Int
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
            @Field("payment_period")paymentPeriod:Int,
            @Field("paid")paid:Int,
            @Field("total_payment")totalPayment:Int,
            @Field("id_payment_account")idPaymentAccount:Int,
            @Field("date")dateTransaction:String
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

    //Get List Transaction Detail
    @Headers("No-Authentication: true")
    @POST("api/transaction/sales")
    fun getTransactionDetail():Call<ResponseGetTransactionDetail>

    //Make Payment Customer
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/transaction/payment")
    fun makePaymentCustomer(
        @Field("id_transaction")idTransaction:Int,
        @Field("paid")paid:Int,
        @Field("id_payment_account")idPaymentAccount:Int,
        @Field("date")datePayment:String
    ):Call<ResponseMakePayment>

    //Logout
    @Headers("No-Authentication: true")
    @POST("api/sales/logout")
    fun logoutSales():Call<ResponseLogout>

    //Get Payment Account
    @Headers("No-Authentication: true")
    @POST("api/transaction/accounts")
    fun getPaymentAccount():Call<ResponseGetPaymentAccounts>

    //GetTodo List
    @Headers("No-Authenticaiton: true")
    @POST("api/sales/todo")
    fun getTodoList():Call<ResponseGetTodoList>

    //Get Customer Level
    @Headers("No-Authentication: true")
    @POST("api/customer/level")
    fun getCustomerLevel():Call<ResponseGetCustomerLevel>

    //Update Photo Sales
    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/sales/photo")
    fun updatePhotoSales(
            @Part photo:MultipartBody.Part?,
    ):Call<ResponseUpdateProfileSales>

    //Update Profile Sales
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/sales/edit")
    fun updateProfileSales(
            @Field("name")name:String,
            @Field("email")email: String,
            @Field("phone")phone: String,
            @Field("address")address: String,
            @Field("gender")photo:String,
            @Field("id_area")idArea:Int
    ):Call<ResponseUpdateProfileSales>

    //Get List Bank
    @Headers("No-Authentication: true")
    @POST("api/transaction/giro/bank")
    fun getListBank():Call<ResponseGetListBank>

    //Add Transaction Giro
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/transaction/giro")
    fun addTransactionGiro(
            @Field("id_transaction")idTransaction:Int,
            @Field("id_bank")idBank:Int,
            @Field("giro_number")giroNumber:String,
            @Field("balance")balance:Long,
            @Field("date_received")dateReceived:String
    ):Call<ResponseAddTransactionGiro>


}
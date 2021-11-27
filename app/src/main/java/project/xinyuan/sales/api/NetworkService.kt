package project.xinyuan.sales.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import project.xinyuan.sales.model.area.ResponseGetListArea
import project.xinyuan.sales.model.bank.ResponseGetListBank
import project.xinyuan.sales.model.company.ResponseGetCompanyList
import project.xinyuan.sales.model.customer.*
import project.xinyuan.sales.model.giro.ResponseAddTransactionGiro
import project.xinyuan.sales.model.payment.ResponseMakePayment
import project.xinyuan.sales.model.paymentaccount.ResponseGetPaymentAccounts
import project.xinyuan.sales.model.product.ResponseGetListProduct
import project.xinyuan.sales.model.sales.*
import project.xinyuan.sales.model.transaction.RequestCreateTransaction
import project.xinyuan.sales.model.transaction.ResponseAddProductTransaction
import project.xinyuan.sales.model.transaction.ResponseAddTransaction
import project.xinyuan.sales.model.transaction.ResponseGetTransactionDetail
import project.xinyuan.sales.model.warehouse.ResponseGetWarehouseList
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    //Authentication
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/sales/login")
    fun loginSales(
            @Field("device_name")deviceName:String,
            @Field("email")email:String,
            @Field("password")password:String
    ):Call<ResponseLogin>

    @Headers("No-Authentication: true")
    @POST("api/sales/detail")
    fun getDetailSales():Call<ResponseGetDetailSales>

    @Headers("No-Authentication: true")
    @POST("api/sales/logout")
    fun logoutSales():Call<ResponseLogout>

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

    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/sales/photo")
    fun updatePhotoSales(
        @Part photo:MultipartBody.Part?,
    ):Call<ResponseUpdateProfileSales>
    //End of Authentication

    //To do List
    @Headers("No-Authenticaiton: true")
    @POST("api/sales/todo")
    fun getTodoList():Call<ResponseGetTodoList>
    //End of To do List

    //Product
    @Headers("No-Authentication: true")
    @POST("api/product")
    fun getListProduct():Call<ResponseGetListProduct>
    //End of Product

    //Area
    @Headers("No-Authentication: true")
    @POST("api/area")
    fun getListArea():Call<ResponseGetListArea>
    //End of Area

    //Customer
    @Headers("No-Authentication: true")
    @POST("api/customer")
    fun getListCustomer():Call<ResponseGetListCustomer>

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

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/customer/idcheck")
    fun checkIdCustomer(
        @Field("administrator_id")administratorId:String
    ):Call<ResponseCheckIdCustomer>

    @Headers("No-Authentication: true")
    @POST("api/customer/level")
    fun getCustomerLevel():Call<ResponseGetCustomerLevel>
    //End of Customer

    //Transaction
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

    @POST("api/transaction")
    fun addTransaction(
        @Body transactionData:RequestCreateTransaction
    ):Call<ResponseAddTransaction>

    @Headers("No-Authentication: true")
    @POST("api/transaction/list")
    fun getTransactionList():Call<ResponseGetTransactionDetail>

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

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/transaction/payment")
    fun makePaymentCustomer(
        @Field("id_transaction")idTransaction:Int,
        @Field("paid")paid:Int,
        @Field("id_payment_account")idPaymentAccount:Int,
        @Field("date")datePayment:String
    ):Call<ResponseMakePayment>

    @Headers("No-Authentication: true")
    @POST("api/transaction/accounts")
    fun getPaymentAccount():Call<ResponseGetPaymentAccounts>

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

    @Headers("No-Authentication: true")
    @POST("api/transaction/giro/bank")
    fun getListBank():Call<ResponseGetListBank>
    //End of Transaction

    //Warehouse
    @Headers("No-Authentication: true")
    @POST("api/warehouse/list")
    fun getWarehouseList():Call<ResponseGetWarehouseList>
    //End of Warehouse

    //Company
    @Headers("No-Authentication: true")
    @POST("api/company/list")
    fun getCompanyList():Call<ResponseGetCompanyList>
    //End of Company
}
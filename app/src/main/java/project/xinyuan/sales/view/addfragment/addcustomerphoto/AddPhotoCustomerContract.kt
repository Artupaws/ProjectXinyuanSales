package project.xinyuan.sales.view.addfragment.addcustomerphoto

import project.xinyuan.sales.model.customer.master.DataCustomer

interface AddPhotoCustomerContract {

    fun messageUploadPhoto(msg:String)
    fun messageRegisterDataCustomer(msg:String)
    fun getIdCustomer(idCustomer: DataCustomer?)

}
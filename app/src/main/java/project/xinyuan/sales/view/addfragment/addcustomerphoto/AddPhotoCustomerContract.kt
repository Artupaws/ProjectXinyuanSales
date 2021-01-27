package project.xinyuan.sales.view.addfragment.addcustomerphoto

import project.xinyuan.sales.model.DataArea
import project.xinyuan.sales.model.DataCustomer

interface AddPhotoCustomerContract {

    fun messageUploadPhoto(msg:String)
    fun messageRegisterDataCustomer(msg:String)
    fun getIdCustomer(idCustomer:DataCustomer?)

}
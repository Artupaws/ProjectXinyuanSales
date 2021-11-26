package project.xinyuan.sales.view.login

interface LoginContract {

    fun messageLogin(code:Int, msg:String)
    fun getTokenLogin(token:String)
    fun showLoading()
    fun hideLoading()

}
package project.xinyuan.sales.view.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityDashboardBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.sales.master.DataSales
import project.xinyuan.sales.view.account.AccountActivity
import project.xinyuan.sales.view.addfragment.AddFragment
import project.xinyuan.sales.view.history.HistoryFragment
import project.xinyuan.sales.view.home.HomeFragment
import project.xinyuan.sales.view.login.LoginActivity
import project.xinyuan.sales.view.todolist.TodoListActivity

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DashboardContract {

    private lateinit var binding:ActivityDashboardBinding
    var doubleTap: Boolean = false
    private val fragmentManager = supportFragmentManager
    private var fragment:Fragment? =null
    private lateinit var presenter: DashboardPresenter
    private lateinit var sharedPref:SharedPreferencesHelper
    private var data: DataSales?=null
    private var popupLogout:Dialog? = null
    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(fragment)

        appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        presenter = DashboardPresenter(this, this)
        sharedPref = SharedPreferencesHelper(this)
        presenter.getDetailSales()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.linearAccount.setOnClickListener(this)
        binding.ivActionTodoList.setOnClickListener(this)
        binding.ivActionSetting.setOnClickListener(this)
        binding.linearReload.setOnClickListener(this)
        fragmentManager.beginTransaction().apply {
            replace(R.id.view_botnav, HomeFragment()).commit()
        }
        stateOpenFragment()
        showPopupLogout()

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo, AppUpdateType.FLEXIBLE, this, 999
                )
            }
        }
    }

    override fun onResume() {
        presenter.getDetailSales()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdateManager.startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE, this, 999)
            }
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999 && resultCode == Activity.RESULT_OK){
            presenter.getDetailSales()
        }else {
            Toast.makeText(applicationContext, "Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stateOpenFragment(){
        val openFragment = intent
        if (openFragment.getIntExtra("openFragment", 0) == 3){
            loadFragment(HistoryFragment())
            binding.bottomNavigationView.menu.findItem(R.id.historyFragment).isChecked = true
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.view_botnav, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.homeFragment -> {
                binding.floatingAdd.setImageResource(R.drawable.ic_add_black)
                fragment = HomeFragment()
            }
            R.id.addFragment -> {
                binding.floatingAdd.setImageResource(R.drawable.ic_add_white)
                fragment = AddFragment()
            }
            R.id.historyFragment -> {
                binding.floatingAdd.setImageResource(R.drawable.ic_add_black)
                fragment = HistoryFragment()
            }
        }
        return loadFragment(fragment)
    }

    override fun onBackPressed() {
        if (doubleTap) {
            super.onBackPressed()
        } else {
            Snackbar.make(binding.viewBotnav, "Please click once again to exit", Snackbar.LENGTH_SHORT).show()
            doubleTap = true
            val handler: Handler = Handler()
            handler.postDelayed({ doubleTap = false }, 500)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.linear_account -> {
                moveDetailSales(data)
            }
            R.id.iv_action_todo_list -> {
                val intent = Intent(applicationContext, TodoListActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_action_setting -> {
                popupLogout?.show()
            }
            R.id.linear_reload -> {
                presenter.getDetailSales()
            }
        }
    }

    private fun reloadProfile(){
        if (binding.tvSalesName.text == ""){
            binding.linearProfile.visibility = View.GONE
            binding.linearReload.visibility = View.VISIBLE
        } else {
            binding.linearProfile.visibility = View.VISIBLE
            binding.linearReload.visibility = View.GONE
        }
    }

    private fun showPopupLogout(){
        @SuppressLint("UserCompatLoadingForDrawables")
        popupLogout = Dialog(this)
        popupLogout?.setContentView(R.layout.popup_ask)
        popupLogout?.setCancelable(true)
        popupLogout?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window : Window = popupLogout?.window!!
        window.setGravity(Gravity.CENTER)
        val tvQuestion = popupLogout?.findViewById<TextView>(R.id.tv_question)
        val btnYes = popupLogout?.findViewById<Button>(R.id.btn_yes)
        val btnNo = popupLogout?.findViewById<Button>(R.id.btn_no)
        tvQuestion?.text = "Are you sure want to logout?"
        btnYes?.setOnClickListener { presenter.logoutSales() }
        btnNo?.setOnClickListener { popupLogout?.dismiss() }
    }


    private fun moveDetailSales(data: DataSales?){
        val intent = Intent(applicationContext, AccountActivity::class.java)
        intent.putExtra("detailSales", data)
        startActivity(intent)
    }

    private fun tryLogin(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun move(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun messageGetDetailSales(msg: String) {
        Log.d("detailSales", msg)
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(applicationContext, "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun messageLogoutSales(msg: String) {
        Log.d("logoutSales", msg)
        popupLogout?.dismiss()
        if (msg.contains("Success")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            move()
        }else{
            Snackbar.make(binding.ivActionSetting, "Logout Failed.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getDetailSales(data: DataSales?) {
        val salesName = data?.name
        val sayHiSales = "Hi, ${data?.name}"
        binding.tvSalesName.text = sayHiSales
        reloadProfile()
        this.data = data
        Glide.with(applicationContext).load(data?.photo).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(binding.ivSales)

        sharedPref.save(Constants.SALES_NAME, salesName!!)
    }
}
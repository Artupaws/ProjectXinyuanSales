package project.xinyuan.sales.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityDashboardBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataSales
import project.xinyuan.sales.view.account.AccountActivity
import project.xinyuan.sales.view.addfragment.AddFragment
import project.xinyuan.sales.view.history.HistoryFragment
import project.xinyuan.sales.view.home.HomeFragment
import project.xinyuan.sales.view.login.LoginActivity

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DashboardContract {

    private lateinit var binding:ActivityDashboardBinding
    var doubleTap: Boolean = false
    private val fragmentManager = supportFragmentManager
    private var fragment:Fragment? =null
    private val itemAddFragment:Int = R.id.addFragment
    private lateinit var presenter: DashboardPresenter
    private lateinit var sharedPref:SharedPreferencesHelper
    private var data:DataSales?=null
    private var openFragment:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(fragment)
        presenter = DashboardPresenter(this, this)
        sharedPref = SharedPreferencesHelper(this)
        presenter.getDetailSales()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.linearAccount.setOnClickListener(this)
        fragmentManager.beginTransaction().apply {
           replace(R.id.view_botnav, HomeFragment()).commit()
       }
        stateOpenFragment()
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
        }
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

    override fun messageGetDetailSales(msg: String) {
        Log.d("detailSales", msg)
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(applicationContext, "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun getDetailSales(dataSales: DataSales?) {
        val salesName = dataSales?.name
        val sayHiSales = "Hi, ${dataSales?.name}"
        binding.tvSalesName.text = sayHiSales
        data = dataSales
        sharedPref.save(Constants.SALES_NAME, salesName!!)
    }
}
package project.xinyuan.sales.view.account

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityAccountBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataSales
import project.xinyuan.sales.view.login.LoginActivity

class AccountActivity : AppCompatActivity(), View.OnClickListener, AccountActivityContract {

    private lateinit var binding:ActivityAccountBinding
    private var popupLogout:Dialog? = null
    private lateinit var sharedPref: SharedPreferencesHelper
    private lateinit var presenter: AccountActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AccountActivityPresenter(this, this)
        sharedPref = SharedPreferencesHelper(applicationContext)
        val dataSales = intent.getParcelableExtra<DataSales>("detailSales")
        binding.tvSalesName.text = dataSales?.name
        Glide.with(this).load(dataSales?.photo).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivProfile)
        binding.ivBack.setOnClickListener(this)
        binding.tvActionLogout.setOnClickListener(this)
        showPopupLogout()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_action_logout -> {
                popupLogout?.show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun move(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
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

    override fun messageLogout(msg: String) {
        Log.d("logout", msg)
        popupLogout?.dismiss()
        if (msg.contains("Success")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            move()
        }else{
            Snackbar.make(binding.tvActionLogout, "Logout Failed.", Snackbar.LENGTH_SHORT).show()
        }
    }
}
package project.xinyuan.sales.view.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.adapter.AdapterListTransaction
import project.xinyuan.sales.databinding.FragmentHistoryBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.login.LoginActivity

class HistoryFragment : Fragment(), HistoryTransactionContract {

    private var _binding:FragmentHistoryBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: HistoryTransactionPresenter
    private lateinit var sharedPref: SharedPreferencesHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        presenter = HistoryTransactionPresenter(this, requireContext())
        sharedPref = SharedPreferencesHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getTransactionDetail()
        refresh()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getTransactionDetail()
        }
    }

    private fun tryLogin(){
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        ActivityCompat.finishAffinity(activity!!)
    }

    override fun messageGetTransactionDetail(msg: String) {
        Log.d("getTransactionDetail", msg)
        binding?.swipeRefresh?.isRefreshing = false
        if (msg.contains("Unauthenticated")){
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.TOKEN_LOGIN)
            Toast.makeText(requireContext(), "you have logged in on another device, please log in again", Toast.LENGTH_SHORT).show()
            tryLogin()
        }
    }

    override fun getDataTransaction(data: List<DataTransaction?>?) {
        binding?.rvTransaction?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterListTransaction(requireContext(), data)
        }
    }
}
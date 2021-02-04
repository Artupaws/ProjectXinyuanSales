package project.xinyuan.sales.view.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.xinyuan.sales.adapter.AdapterListTransaction
import project.xinyuan.sales.databinding.FragmentHistoryBinding
import project.xinyuan.sales.model.DataTransaction

class HistoryFragment : Fragment(), HistoryTransactionContract {

    private var _binding:FragmentHistoryBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: HistoryTransactionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HistoryTransactionPresenter(this, requireContext())
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

    override fun messageGetTransactionDetail(msg: String) {
        Log.d("getTransactionDetail", msg)
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun getDataTransaction(data: List<DataTransaction?>?) {
        binding?.rvTransaction?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterListTransaction(requireContext(), data)
        }
    }
}
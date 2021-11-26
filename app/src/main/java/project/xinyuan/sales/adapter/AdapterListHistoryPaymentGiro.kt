package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemPaymentGiroBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.model.giro.master.DataGiro

class AdapterListHistoryPaymentGiro(val context: Context, private val listGiro: List<DataGiro?>?):RecyclerView.Adapter<AdapterListHistoryPaymentGiro.Holder>() {
    private lateinit var helper:Helper
    private var paymentAccount:String? = null
    private var balance:String? = null
    private var status:Int? = null

    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemPaymentGiroBinding.bind(view)
        fun bin(item: DataGiro){
            with(binding) {
                tvDateReceived.text = item.dateReceived
                tvBankName.text = item.bankName
                tvGiroNumber.text = item.giroNumber.toString()
                balance = helper.convertToFormatMoneyIDR(item.balance.toString())
                tvBalance.text = balance
                status = item.status
                setStatus(binding, status)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHistoryPaymentGiro.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaymentGiroBinding.inflate(inflater)
        helper = Helper()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHistoryPaymentGiro.Holder, position: Int) {
        val payment : DataGiro = listGiro?.get(position)!!
        holder.bin(payment)
    }

    override fun getItemCount(): Int {
        return listGiro?.size!!
    }

    private fun setStatus(binding:ListItemPaymentGiroBinding, statusGiro:Int?){
        if (statusGiro == 1){
            binding.tvStatus.isEnabled = true
            binding.tvStatus.text = "Received"
        } else {
            binding.tvStatus.isEnabled = false
            binding.tvStatus.text = "Not Received"
        }
    }
}
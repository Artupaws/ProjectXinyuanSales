package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemPaymentBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.model.DataPayment

class AdapterListHistoryPayment(val context: Context, private val listPayment: List<DataPayment?>?):RecyclerView.Adapter<AdapterListHistoryPayment.Holder>() {
    private lateinit var helper:Helper

    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemPaymentBinding.bind(view)
        fun bin(item: DataPayment){
            with(binding) {
                tvDate.text = item.date
                tvTotalPayment.text = helper.convertToFormatMoneyIDR(item.paid!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHistoryPayment.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaymentBinding.inflate(inflater)
        helper = Helper()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHistoryPayment.Holder, position: Int) {
        val payment : DataPayment = listPayment?.get(position)!!
        holder.bin(payment)
    }

    override fun getItemCount(): Int {
        return listPayment?.size!!
    }
}
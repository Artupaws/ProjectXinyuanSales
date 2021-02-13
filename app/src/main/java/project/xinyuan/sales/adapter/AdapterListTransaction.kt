package project.xinyuan.sales.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ListItemTransactionBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.history.detailtransaction.DetailTransactionActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AdapterListTransaction(val context: Context, private val listTransaction:List<DataTransaction?>?):
        RecyclerView.Adapter<AdapterListTransaction.Holder>(), Filterable {

    var listTransactionFilter = ArrayList<DataTransaction?>()
    init {
        listTransactionFilter = listTransaction as ArrayList<DataTransaction?>
    }

    private val positionCollapse: Animation by lazy {
        AnimationUtils.loadAnimation(
                context,
                R.anim.anim_collapse
        )
    }
    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(
                context,
                R.anim.anim_expand
        )
    }
    private var clicked = true

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemTransactionBinding.bind(view)
        fun bin(item: DataTransaction){
            with(binding){
               tvDate.text = item.date
                tvInvoiceNumber.text = item.invoiceNumber
                tvNameCustomer.text = item.customer
                tvTypePayment.text = item.payment
                btnDetail.setOnClickListener {
                    val intent = Intent(context, DetailTransactionActivity::class.java)
                    intent.putExtra("dataTransaction", item)
                    context.startActivity(intent)
                }
                linearHistoryPayment.setOnClickListener {
                    onAddPostClicked(binding)
                }
                rvHistoryPayment.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = AdapterListHistoryPayment(context, item.transactionpayment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListTransaction.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTransactionBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListTransaction.Holder, position: Int) {
        val transaction: DataTransaction = listTransactionFilter[position]!!
        holder.bin(transaction)
    }

    override fun getItemCount():Int = listTransactionFilter.size

    private fun onAddPostClicked(binding: ListItemTransactionBinding) {
        setVisibility(clicked, binding)
        setAnimation(clicked, binding)
        clicked = !clicked
    }

    private fun setAnimation(clicked:Boolean, binding:ListItemTransactionBinding){
        if (!clicked){
            binding.ivExpand.startAnimation(positionCollapse)
        } else {
            binding.ivExpand.startAnimation(positionExpand)
        }
    }

    private fun setVisibility(clicked: Boolean, binding: ListItemTransactionBinding){
        if (!clicked){
            binding.linearTitle.visibility = View.GONE
            binding.rvHistoryPayment.visibility = View.GONE
        } else {
            binding.linearTitle.visibility = View.VISIBLE
            binding.rvHistoryPayment.visibility = View.VISIBLE
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch==null || querySearch.isEmpty()){
                    listTransaction
                } else {
                    listTransaction?.filter {
                        it?.invoiceNumber?.toLowerCase()!!.contains(querySearch)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listTransactionFilter = p1?.values as ArrayList<DataTransaction?>
                notifyDataSetChanged()
            }

        }
    }
}
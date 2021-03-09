package project.xinyuan.sales.adapter

import android.content.Context
import android.content.Intent
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
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.history.detailtransaction.DetailTransactionActivity
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
                    clickPayment(binding)
                }
                linearHistoryPaymentGiro.setOnClickListener {
                    clickedPaymentGiro(binding)
                }
                rvHistoryPayment.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = AdapterListHistoryPayment(context, item.transactionpayment)
                }
                rvHistoryPaymentGiro.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = AdapterListHistoryPaymentGiro(context, item.giropayment)
                }
                setStatusLoan(item.debt!!, binding)
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

    private fun clickedPaymentGiro(binding: ListItemTransactionBinding){
        setVisibilityGiro(clicked, binding)
        setAnimationGiro(clicked, binding)
        clicked = !clicked
    }

    private fun clickPayment(binding: ListItemTransactionBinding) {
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

    private fun setAnimationGiro(clicked:Boolean, binding:ListItemTransactionBinding){
        if (!clicked){
            binding.ivExpandGiro.startAnimation(positionCollapse)
        } else {
            binding.ivExpandGiro.startAnimation(positionExpand)
        }
    }

    private fun setStatusLoan(debt:Int, binding: ListItemTransactionBinding){
        if (debt == 0){
            binding.tvStatusPayment.text = context.getString(R.string.paid_off)
            binding.tvStatusPayment.isEnabled = true
        } else {
            binding.tvStatusPayment.text = context.getString(R.string.not_paid_off)
            binding.tvStatusPayment.isEnabled = false
        }
    }

    private fun setVisibility(clicked: Boolean, binding: ListItemTransactionBinding){
        if (!clicked){
            binding.linearTitlePayment.visibility = View.GONE
            binding.rvHistoryPayment.visibility = View.GONE
        } else {
            binding.linearTitlePayment.visibility = View.VISIBLE
            binding.rvHistoryPayment.visibility = View.VISIBLE
        }
    }

    private fun setVisibilityGiro(clicked: Boolean, binding: ListItemTransactionBinding){
        if (!clicked){
            binding.linearPaymentGiro.visibility = View.GONE
            binding.rvHistoryPaymentGiro.visibility = View.GONE
        } else {
            binding.linearPaymentGiro.visibility = View.VISIBLE
            binding.rvHistoryPaymentGiro.visibility = View.VISIBLE
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
package project.xinyuan.sales.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemTransactionBinding
import project.xinyuan.sales.model.DataTransaction
import project.xinyuan.sales.view.history.detailtransaction.DetailTransactionActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterListTransaction(val context: Context, private val listTransaction:List<DataTransaction?>?): RecyclerView.Adapter<AdapterListTransaction.Holder>() {
    private var dateNow:String?=null

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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListTransaction.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTransactionBinding.inflate(inflater)
        setDate()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListTransaction.Holder, position: Int) {
        val transaction: DataTransaction = listTransaction?.get(position)!!
        holder.bin(transaction)
    }

    override fun getItemCount():Int = listTransaction?.size!!

    private fun setDate(){
        dateNow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val dueDate = LocalDateTime.now()
            val formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = dueDate.format(formatDate)
            date
        }else{
            val currentDate = Date()
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val date = formatter.format(currentDate)
            date
        }
    }
}
package project.xinyuan.sales.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.model.TransactiondetailsItem
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterListProductDetailTransaction(val context: Context, private val listProduct: List<TransactiondetailsItem?>?):RecyclerView.Adapter<AdapterListProductDetailTransaction.Holder>() {
//    var totalPayment:Int?=null
    var productName:String = ""
    val helper: Helper = Helper()
    private var dateNow:String?=null
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderBinding.bind(view)
        fun bin(item: TransactiondetailsItem){
            with(binding) {
                productName = "${item.dataProduct?.type} ${item.dataProduct?.colour} ${item.dataProduct?.size}"
                tvProductName.text = productName
                tvTotalOrder.text = item.quantity.toString()
                tvYourPrice.text = helper.convertToFormatMoneyIDR(item.price.toString())
                tvSubTotalPrice.text = helper.convertToFormatMoneyIDR(item.subTotal.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProductDetailTransaction.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartOrderBinding.inflate(inflater)
        setDate()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProductDetailTransaction.Holder, position: Int) {
        val cart : TransactiondetailsItem = listProduct?.get(position)!!
        holder.bin(cart)
    }

    override fun getItemCount(): Int {
        return listProduct?.size!!
    }

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
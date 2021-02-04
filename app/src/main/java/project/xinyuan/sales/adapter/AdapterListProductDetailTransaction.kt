package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.model.TransactiondetailsItem

class AdapterListProductDetailTransaction(val context: Context, private val listProduct: List<TransactiondetailsItem?>?):RecyclerView.Adapter<AdapterListProductDetailTransaction.Holder>() {
    var totalPayment:Int?=null
    var productName:String = ""
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderBinding.bind(view)
        fun bin(item: TransactiondetailsItem){
            with(binding) {
                productName = "${item.dataProduct?.type} ${item.dataProduct?.colour} ${item.dataProduct?.size}"
                tvProductName.text = productName
                tvTotalOrder.text = item.quantity.toString()
                tvYourPrice.text = item.price.toString()
                tvSubTotalPrice.text = item.subTotal.toString()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProductDetailTransaction.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartOrderBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProductDetailTransaction.Holder, position: Int) {
        val cart : TransactiondetailsItem = listProduct?.get(position)!!
        holder.bin(cart)
    }

    override fun getItemCount(): Int {
        return listProduct?.size!!
    }
}
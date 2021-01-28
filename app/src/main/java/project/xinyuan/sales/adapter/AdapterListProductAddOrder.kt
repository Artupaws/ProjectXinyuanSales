package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import project.xinyuan.sales.databinding.ListItemProductAddOrderBinding
import project.xinyuan.sales.model.DataProduct

class AdapterListProductAddOrder(val context: Context, private val listProduct:List<DataProduct?>?):RecyclerView.Adapter<AdapterListProductAddOrder.Holder>() {
    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        private val binding = ListItemProductAddOrderBinding.bind(view)
        fun bin(item:DataProduct){
            with(binding){
                tvProductName.text = item.type
                tvProductPrice.text = item.cost.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProductAddOrder.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProductAddOrderBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProductAddOrder.Holder, position: Int) {
        val product: DataProduct = listProduct?.get(position)!!
        holder.bin(product)
    }

    override fun getItemCount(): Int = listProduct?.size!!
}
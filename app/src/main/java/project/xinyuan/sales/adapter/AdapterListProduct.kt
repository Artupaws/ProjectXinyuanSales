package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.xinyuan.sales.databinding.ListItemProductBinding
import project.xinyuan.sales.model.DataProduct

class AdapterListProduct(val context: Context, private val listProduct:List<DataProduct?>?): RecyclerView.Adapter<AdapterListProduct.Holder>() {

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemProductBinding.bind(view)
        fun bin(item:DataProduct){
            with(binding){
                Glide.with(context).load(item.photo.toString()).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivProduct)
                tvTypeProduct.text = item.type
                tvSizeProduct.text = item.size
                tvPriceProduct.text = item.cost.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProduct.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProductBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProduct.Holder, position: Int) {
        val product: DataProduct = listProduct?.get(position)!!
        holder.bin(product)
    }

    override fun getItemCount():Int = listProduct?.size!!
}
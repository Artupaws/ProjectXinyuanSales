package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.xinyuan.sales.databinding.ListItemProductBinding
import project.xinyuan.sales.model.DataProduct
import java.util.ArrayList

class AdapterListProduct(val context: Context, private val listProduct:List<DataProduct?>?):
        RecyclerView.Adapter<AdapterListProduct.Holder>(), Filterable {

    var listProductFilter = ArrayList<DataProduct?>()
    init {
        listProductFilter = listProduct as ArrayList<DataProduct?>
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemProductBinding.bind(view)
        fun bin(item:DataProduct){
            with(binding){
                Glide.with(context).load(item.photo.toString()).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivProduct)
                val typeProduct = "Type : ${item.type}"
                val sizeProduct = "Size : ${item.size}"
                val color = "Color : ${item.colour}"
                val factoryName = "Factory : ${item.factory}"
                if(item.grossWeight == null){
                    tvGrossWeight.text = "Gross Weight (kg): -"
                }else {
                    val grossWeight = "Gross Weight (kg): ${item.grossWeight}"
                    tvGrossWeight.text = grossWeight
                }
                tvTypeProduct.text = typeProduct
                tvSizeProduct.text = sizeProduct
                tvColorProduct.text = color
                tvCompanyName.text = factoryName

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProduct.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProductBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProduct.Holder, position: Int) {
        val product: DataProduct = listProductFilter[position]!!
        holder.bin(product)
    }

    override fun getItemCount():Int = listProductFilter.size

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch==null || querySearch.isEmpty()){
                    listProduct
                } else {
                    listProduct?.filter {
                        it?.type?.toLowerCase()!!.contains(querySearch)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listProductFilter = p1?.values as ArrayList<DataProduct?>
                notifyDataSetChanged()
            }

        }
    }
}
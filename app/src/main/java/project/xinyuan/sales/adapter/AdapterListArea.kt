package project.xinyuan.sales.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemAreaBinding
import project.xinyuan.sales.model.DataArea

class AdapterListArea(val context: Context, private val listArea:List<DataArea?>?): RecyclerView.Adapter<AdapterListArea.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemAreaBinding.bind(view)
        fun bin(item:DataArea){
            with(binding){
             tvAreaName.text = item.name
                tvAreaName.setOnClickListener {
                    val sendArea = Intent("areaCustomer")
                        .putExtra("areaName", item.name)
                        .putExtra("areaId", item.id)
                    broadcaster?.sendBroadcast(sendArea)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListArea.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAreaBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListArea.Holder, position: Int) {
        val area: DataArea = listArea?.get(position)!!
        holder.bin(area)
    }

    override fun getItemCount():Int = listArea?.size!!
}
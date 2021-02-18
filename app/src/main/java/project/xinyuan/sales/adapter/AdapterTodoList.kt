package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemTodoBinding
import project.xinyuan.sales.model.DataArea
import project.xinyuan.sales.model.DataTodo

class AdapterTodoList(val context: Context, private val listTodo:List<DataTodo?>?): RecyclerView.Adapter<AdapterTodoList.Holder>() {
    private var status:Int? = null

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemTodoBinding.bind(view)
        fun bin(item:DataTodo){
            with(binding){
                tvTodo.text = item.message
                tvDueDate.text = item.doneDate
                status = item.done
                btnStatusTodo.isEnabled = status == 1
                if (status == 1){
                    btnStatusTodo.text = "Done"
                } else {
                    btnStatusTodo.text = "Not Yet"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTodoList.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTodoBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTodoList.Holder, position: Int) {
        val todo: DataTodo = listTodo?.get(position)!!
        holder.bin(todo)
    }

    override fun getItemCount():Int = listTodo?.size!!
}
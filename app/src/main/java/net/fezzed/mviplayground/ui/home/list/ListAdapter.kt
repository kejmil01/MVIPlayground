package net.fezzed.mviplayground.ui.home.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.fezzed.mviplayground.R
import net.fezzed.mviplayground.databinding.ItemLayoutBinding
import net.fezzed.mviplayground.ui.home.model.ItemModel

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {

    var itemList: MutableList<ItemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_layout,
            parent,
            false
        )
        return ListItemViewHolder(binding, binding.root)
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].fullName.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    class ListItemViewHolder(
        private val binding: ItemLayoutBinding,
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: ItemModel) {
            binding.model = model
            binding.invalidateAll()
        }
    }
}
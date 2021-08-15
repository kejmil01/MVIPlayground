package net.fezzed.mviplayground.ui.home.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.fezzed.mviplayground.ui.home.model.ItemModel

class ListRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
	RecyclerView(context, attrs, defStyleAttr) {

	private val adapter = ListAdapter()

	constructor(context: Context) : this(context, null)

	constructor(context: Context, attrs: AttributeSet?) : this(
		context,
		attrs,
		R.attr.recyclerViewStyle
	)

	init {
		adapter.setHasStableIds(true)
		setAdapter(adapter)
		layoutManager = LinearLayoutManager(context)
	}

	fun setItems(items: List<ItemModel>) {
		adapter.itemList.clear()
		adapter.itemList.addAll(items)
		adapter.notifyDataSetChanged()
	}
}
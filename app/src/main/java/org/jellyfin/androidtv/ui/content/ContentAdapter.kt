package org.jellyfin.androidtv.ui.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jellyfin.androidtv.R
import org.jellyfin.sdk.model.api.BaseItemDto

class ContentAdapter(
    private val focusListener: OnItemFocusListener
) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private val items = mutableListOf<BaseItemDto>()

    fun submitList(newItems: List<BaseItemDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name ?: "Unknown"

        // notify fragment when focused
        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) focusListener.onItemFocused(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(android.R.id.text1)
    }

    interface OnItemFocusListener {
        fun onItemFocused(item: BaseItemDto)
    }
}

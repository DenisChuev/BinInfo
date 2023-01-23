package dc.bininfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dc.bininfo.dao.Bin

class BinListAdapter : ListAdapter<Bin, BinListAdapter.BinViewHolder>(BinComparator()) {

    class BinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binText: TextView = itemView.findViewById(R.id.bin_text)
        private val schemeText: TextView = itemView.findViewById(R.id.scheme_text)
        private val typeText: TextView = itemView.findViewById(R.id.type_text)

        fun bind(bin: Bin) {
            binText.text = bin.binNum
            schemeText.text = bin.scheme
            typeText.text = bin.type
        }
    }

    class BinComparator : DiffUtil.ItemCallback<Bin>() {
        override fun areItemsTheSame(oldItem: Bin, newItem: Bin): Boolean {
            return oldItem.binNum == newItem.binNum
        }

        override fun areContentsTheSame(oldItem: Bin, newItem: Bin): Boolean {
            return oldItem.binNum == newItem.binNum
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_bin_item, parent, false)
        return BinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package dc.bininfo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dc.bininfo.R
import dc.bininfo.dao.Bin
import dc.bininfo.databinding.HistoryBinItemBinding

class BinListAdapter : ListAdapter<Bin, BinListAdapter.BinViewHolder>(BinComparator()) {

    class BinViewHolder(private val itemBinding: HistoryBinItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(bin: Bin) {
            itemBinding.binText.text = bin.binNum
            itemBinding.schemeText.text = bin.scheme
            itemBinding.typeText.text = bin.type
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
        return BinViewHolder(
            HistoryBinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
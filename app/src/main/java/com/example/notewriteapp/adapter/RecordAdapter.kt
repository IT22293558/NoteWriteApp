package com.example.notewriteapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notewriteapp.databinding.RecordLayoutBinding

import com.example.notewriteapp.fragments.HomeFragmentDirections
import com.example.notewriteapp.model.Record

class RecordAdapter:RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

         class RecordViewHolder(val itemBinding: RecordLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)



    private val differCallback = object :DiffUtil.ItemCallback<Record>(){
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.recordDesc == newItem.recordDesc &&
                    oldItem.recordTitle == newItem.recordTitle
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            RecordLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
         return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentRecord = differ.currentList[position]

        holder.itemBinding.noteTitle.text = currentRecord.recordTitle
        holder.itemBinding.noteDesc.text = currentRecord.recordDesc

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment2ToEditRecordFragment2(currentRecord)
            it.findNavController().navigate(direction)
        }
    }
}





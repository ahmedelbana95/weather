package com.app.task.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<B : ViewDataBinding, D : Any>(
    open var context: Context,
    open var listData: List<D>
) : RecyclerView.Adapter<BaseAdapter<B, D>.MyViewHolder>() {

    lateinit var binding: B

    inner class MyViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root)

    abstract fun layoutID(): Int

    override fun getItemCount() = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutID(), parent, false)
        return MyViewHolder(binding)
    }
}

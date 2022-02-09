package com.ozetest.www

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.motion.utils.ViewState
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class FooterAdapter(): LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
     val loaderLayout: RelativeLayout = view.findViewById(R.id.loader_layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.loaderLayout.visibility =  if (loadState == LoadState.Loading) View.VISIBLE else View.GONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.footer_loading_layout, parent, false)
        )
    }
}
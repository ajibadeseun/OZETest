package com.ozetest.www

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainListAdapter(private val context: Context, private val viewModel: MainViewModel) :
    PagingDataAdapter<Github, MainListAdapter.ViewHolder>(DataDifferentiator) {
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ShapeableImageView = view.findViewById(R.id.image)
        val userName: TextView = view.findViewById(R.id.textView)
        val favoriteBtn: AppCompatCheckBox = view.findViewById(R.id.fav_btn)
        val layout:RelativeLayout = view.findViewById(R.id.layout)
    }

    object DataDifferentiator : DiffUtil.ItemCallback<Github>() {

        override fun areItemsTheSame(oldItem: Github, newItem: Github): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Github, newItem: Github): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Customizing the progress bar showing the flag
        circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 15f
        circularProgressDrawable.start()

        val data = getItem(position)

        //Loading image Url
        val imgUrl = data?.imageUrl
        imgUrl.let {
            val imgUri = imgUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            holder.profileImage.load(imgUri) {
                placeholder(circularProgressDrawable)
                error(R.drawable.ic_error_img)
            }
        }

        //Setting profile name
        holder.userName.text = data?.userName

        if (data != null) {
            holder.favoriteBtn.isChecked = data.isFavorite
        }
        //Clicking on the Favorite button
        holder.favoriteBtn.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { v: CompoundButton?, b: Boolean ->
            holder.favoriteBtn.isChecked = b
            //Setting the value of isFavorite flag
            data?.isFavorite = b
            //Updating the Github user isFavorite flag
            updateItem(data)
        })

        holder.layout.setOnClickListener {
            val bundle = Bundle()
            bundle.apply {
                putString("userName",data?.userName)
                putString("imageString",data?.imageUrl)
                putString("profileUri",data?.profileUrl)
            }
            val intent = Intent(context, ProfileActivity::class.java).apply { putExtras(bundle)}
            context.startActivity(intent)
        }
    }


    /**
     * Updating the User isFavorite status
     */
    private fun updateItem(github: Github?) {
        GlobalScope.launch {
            viewModel.updateItem(github)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.profile_list_item, parent, false)
        )
    }
}
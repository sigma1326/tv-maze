package com.example.tvmaze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmaze.model.Show
import com.example.tvmaze.R
import com.example.tvmaze.ui.home.Home
import com.example.tvmaze.utils.CustomUtils
import kotlinx.android.synthetic.main.show_item.view.*

class GridRecyclerAdapter(val shows: MutableList<Show>, private val clicker: Home.MyClickListener) : RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>() {


    class ViewHolder(v: View, clicker: Home.MyClickListener) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private lateinit var show: Show

        init {
            v.setOnClickListener {
                clicker.onClick(it, show.id)
            }
        }

        fun bind(show: Show) {
            this.show = show
            CustomUtils.initShowView(view.img_card, show, true)
        }


    }



    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridRecyclerAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.show_item, false)
        return ViewHolder(inflatedView, clicker)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: GridRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(shows[position])
    }
}


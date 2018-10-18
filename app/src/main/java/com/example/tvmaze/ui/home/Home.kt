package com.example.tvmaze.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvmaze.R
import com.example.tvmaze.adapter.GridRecyclerAdapter
import com.example.tvmaze.model.Show
import com.example.tvmaze.network.NetworkRepositoryImpl
import com.example.tvmaze.network.NetworkResponse
import com.example.tvmaze.utils.CustomUtils
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class Home : Fragment() {

    private val network: NetworkRepositoryImpl by inject()

    private var page = 0
    private val shows: MutableList<Show> = emptyList<Show>().toMutableList()

    private lateinit var adapter: GridRecyclerAdapter

    private lateinit var vm: HomeViewModel

    companion object {
        fun newInstance() = Home()
    }

    interface MyClickListener {
        fun onClick(v: View, id: Int)
    }

    private val clicker = object : MyClickListener {
        override fun onClick(v: View, id: Int) {
            val action = HomeDirections.goToShow()
            action.setShowId(id)
            NavHostFragment.findNavController(this@Home).navigate(action)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getViewModel()
//        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(activity, 2)
        rvCards.layoutManager = gridLayoutManager
        adapter = GridRecyclerAdapter(shows, clicker)
        rvCards.itemAnimator = DefaultItemAnimator()
        rvCards.setHasFixedSize(true)
        rvCards.setItemViewCacheSize(20)
        rvCards.isDrawingCacheEnabled = true
        rvCards.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        //disable the scrollview
        ViewCompat.setNestedScrollingEnabled(rvCards, false)

        img_search.setOnClickListener { it ->
            CustomUtils.rotateSmoothView(it)
            val action = HomeDirections.goToSearch()
            NavHostFragment.findNavController(this@Home).navigate(action)
        }

        swipeRefreshLayout.setAnimateToRefreshInterpolator(LinearInterpolator())
        swipeRefreshLayout.setAnimateToStartInterpolator(LinearInterpolator())
        swipeRefreshLayout.setOnRefreshListener {
            page = 0
            loadShows(page)
        }

        //init the shows
        if (page == 0) {
            loadShows(page++)
        } else {
            rvCards.adapter = adapter
        }
    }

    private fun loadShows(p: Int) {
        network.getShowsByPage(p, object : NetworkResponse {
            override fun onSuccess(response: String, responseObjects: MutableList<Show>) {
                CustomUtils.makeToast(activity, "بروزرسانی شد").show()
                CustomUtils.getHandler().post {
                    shows.addAll(responseObjects)

                    adapter.shows.addAll(shows)
                    rvCards.adapter = adapter

                    // refresh complete
                    swipeRefreshLayout.setRefreshing(false)
                }
            }

            override fun onError(errorMessage: String) {
                CustomUtils.makeToast(activity, errorMessage).show()
                Log.d("Result", errorMessage)
                // refresh complete
                swipeRefreshLayout.setRefreshing(false)
            }
        })
    }

}

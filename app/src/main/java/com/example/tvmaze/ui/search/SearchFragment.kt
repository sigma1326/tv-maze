package com.example.tvmaze.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvmaze.R
import com.example.tvmaze.adapter.GridRecyclerAdapter
import com.example.tvmaze.model.SearchResult
import com.example.tvmaze.model.Show
import com.example.tvmaze.network.NetworkRepositoryImpl
import com.example.tvmaze.network.NetworkResponseSearch
import com.example.tvmaze.ui.home.Home
import com.example.tvmaze.utils.CustomUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.pawegio.kandroid.runOnUiThread
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private val network: NetworkRepositoryImpl by inject()

    private val shows: MutableList<Show> = emptyList<Show>().toMutableList()

    private lateinit var adapter: GridRecyclerAdapter


    private val clicker = object : Home.MyClickListener {
        override fun onClick(v: View, id: Int) {
            val action = SearchFragmentDirections.goToShowFromSearch()
            action.setShowId(id)
            NavHostFragment.findNavController(this@SearchFragment).navigate(action)
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }


    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_search.requestFocus()

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


        RxTextView.textChanges(et_search)
                .filter { it.length >= 3 }
                .subscribe(
                        {
                            Log.d("result", "$it")
                            searchShows(it.toString())
                        },
                        { Log.e("result", "$it") }
                )
    }

    private fun searchShows(query: String) {
        //clear last search results
        runOnUiThread {
            shows.clear()
            adapter.shows.clear()
            rvCards.adapter = adapter
        }

        network.searchShows(query, object : NetworkResponseSearch {
            override fun onSuccess(response: String, responseObjects: MutableList<SearchResult>) {
                CustomUtils.getHandler().post {
                    responseObjects.forEach {
                        shows.add(it.show!!)
                    }
                    adapter.shows.addAll(shows)
                    rvCards.adapter = adapter
                }
            }

            override fun onError(errorMessage: String) {
                CustomUtils.makeToast(activity, errorMessage).show()
                Log.d("Result", errorMessage)
            }
        })
    }

}

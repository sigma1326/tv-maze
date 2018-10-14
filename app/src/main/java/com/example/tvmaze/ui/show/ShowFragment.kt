package com.example.tvmaze.ui.show

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.tvmaze.R
import com.example.tvmaze.model.Show
import com.example.tvmaze.network.NetworkRepositoryImpl
import com.example.tvmaze.network.NetworkResponse
import com.example.tvmaze.utils.CustomUtils
import kotlinx.android.synthetic.main.show_fragment.*
import org.koin.android.ext.android.inject

class ShowFragment : Fragment() {

    private val network: NetworkRepositoryImpl by inject()

    companion object {
        fun newInstance() = ShowFragment()
    }

    private lateinit var viewModel: ShowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.show_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadShow(arguments!!.getInt("show_id"))

        img_back.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                NavHostFragment.findNavController(this@ShowFragment).popBackStack()
            }
            false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowViewModel::class.java)
    }

    private fun loadShow(id: Int) {
        network.getShow(id.toString(), object : NetworkResponse {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(response: String, responseObjects: List<Show>) {
                CustomUtils.makeToast(activity, "بروزرسانی شد").show()
                CustomUtils.getHandler().post {
                    CustomUtils.initShowView(img_card, responseObjects[0], false)
                    sublabel.text = responseObjects[0].name.toString()
                    name.text = responseObjects[0].name.toString()
                    premiered.text = responseObjects[0].premiered.toString()
                    runtime.text = responseObjects[0].runtime.toString() + " min"
                    time.text = responseObjects[0].schedule?.time ?: "NA"
                    if (responseObjects[0].schedule?.days!!.isNotEmpty())
                        days.text = responseObjects[0].schedule?.days?.get(0) ?: "NA"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        summary.text = Html.fromHtml(responseObjects[0].summary, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        summary.text = Html.fromHtml(responseObjects[0].summary)
                    }
                }
            }

            override fun onError(errorMessage: String) {
                CustomUtils.makeToast(activity, errorMessage).show()
                Log.d("Result", errorMessage)
            }
        })
    }
}

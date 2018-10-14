package com.example.tvmaze.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tvmaze.R
import com.example.tvmaze.model.Show
import com.squareup.picasso.Picasso


object CustomUtils {

    fun fullScreen(activity: Activity, window: Window) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun hideKeyboard(activity: Activity?) {
        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusView = activity.currentFocus
        if (focusView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        if (activity.currentFocus != null) {
            activity.currentFocus!!.clearFocus()
        }
    }

    fun rotateSmoothView(view: View) {
        val rotate = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 500
        rotate.repeatCount = 0
        view.animation = rotate
    }

    fun getHandler() = Handler(Looper.getMainLooper())

    fun isConnectedToNetwork(activity: Activity): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }


    fun makeToast(activity: Activity?, toastText: String): Toast {
        val layoutInflater = LayoutInflater.from(activity)
        val layout = layoutInflater.inflate(R.layout.custom_toast, activity!!.findViewById(R.id.custom_toast_container))

        val text = layout.findViewById(R.id.text) as TextView
        text.text = toastText

        val toast = Toast(activity)
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        return toast
    }


    fun initShowView(view: View, show: Show, medium: Boolean) {
        if (medium) {
            Picasso.get().load(show.image?.medium).into(view as ImageView)
        } else {
            Picasso.get().load(show.image?.original).into(view as ImageView)
        }
    }


}


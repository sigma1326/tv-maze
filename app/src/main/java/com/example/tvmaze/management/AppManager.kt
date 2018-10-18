package com.example.tvmaze.management

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.tvmaze.database.DataBase
import com.example.tvmaze.database.DataBaseRepositoryImpl
import com.example.tvmaze.network.NetworkRepositoryImpl
import com.example.tvmaze.network.ShowsRepository
import com.example.tvmaze.ui.home.HomeViewModel
import com.example.tvmaze.ui.search.SearchViewModel
import com.example.tvmaze.ui.show.ShowViewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class AppManager : Application() {
    override fun onCreate() {
        super.onCreate()

        getUncaughtExceptions()

        //start Koin DI
        startKoin(this, listOf(appModule))

    }

    private fun getUncaughtExceptions() {
        // Setup handler for uncaught exceptions.
        try {
            Thread.setDefaultUncaughtExceptionHandler { thread: Thread, throwable: Throwable ->
                Log.e(TAG, "Uncaught Exception thread: " + thread.name + "" + throwable.stackTrace)
                throwable.printStackTrace()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Could not set the Default Uncaught Exception Handler:" + e.stackTrace)
        }
    }


    companion object {
        private const val host = "http://api.tvmaze.com/"
        const val TAG = "debug13"
        private const val DB_NAME = "demo-db"


        private val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(host)
                .build()!!
        val networkService = retrofit.create(ShowsRepository::class.java)!!


        val appModule = module {
            //Network
            single { NetworkRepositoryImpl(get()) }

            //DataBase
            single { Room.databaseBuilder(androidApplication(), DataBase::class.java, DB_NAME).build() }
            single { DataBaseRepositoryImpl(get()) }

            //view model
            viewModel("home") { HomeViewModel(get(), get()) }
            viewModel("search") { SearchViewModel(get(), get()) }
            viewModel("show") { ShowViewModel(get(), get()) }
        }
    }
}
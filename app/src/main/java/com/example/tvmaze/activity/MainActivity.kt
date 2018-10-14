package com.example.tvmaze.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tvmaze.R
import com.example.tvmaze.management.PermissionManager
import com.example.tvmaze.network.NetworkRepositoryImpl
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val network: NetworkRepositoryImpl by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionManager.checkAndAskPermissions(this)



    }


}

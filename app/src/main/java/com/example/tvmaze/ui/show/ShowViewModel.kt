package com.example.tvmaze.ui.show

import androidx.lifecycle.ViewModel
import com.example.tvmaze.database.DataBaseRepositoryImpl
import com.example.tvmaze.network.NetworkRepositoryImpl

class ShowViewModel(val db: DataBaseRepositoryImpl, val network: NetworkRepositoryImpl) : ViewModel() {

}

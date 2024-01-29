package com.example.openinapp.ui.ViewModel

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapp.data.model.RecentLink
import com.example.openinapp.domain.repository.DashBoardRepository
import com.example.openinapp.ui.data.TopItemsModel
import com.example.openinapp.utils.ApiState
import com.example.openinapp.utils.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: DashBoardRepository,
    private val application: Application
) : ViewModel() {

    private var _linksList: MutableLiveData<List<RecentLink>> = MutableLiveData<List<RecentLink>>()
    private var _topItemsList: MutableLiveData<List<TopItemsModel>> =
        MutableLiveData<List<TopItemsModel>>()
    private var _lineSet: MutableLiveData<List<Pair<String, Float>>> =
        MutableLiveData<List<Pair<String, Float>>>()
    private var _greeting: MutableLiveData<String> = MutableLiveData()


    val linksList: LiveData<List<RecentLink>>? = _linksList
    val topItemsList: LiveData<List<TopItemsModel>>? = _topItemsList
    val lineSet: LiveData<List<Pair<String, Float>>>? = _lineSet
    val greeting: LiveData<String>? = _greeting

    fun getData() {

       if(Network.isInternetConnected(applicaion = application)){

        setGreeting()
        viewModelScope.launch {

            val Apiresult = repository.doNetworkCall()
            Apiresult.collect() { it ->
                when (it) {
                    is ApiState.Success -> {
                        _linksList!!.postValue(it.data.data.recent_links)
                        _topItemsList!!.postValue(
                            listOf(
                                TopItemsModel(
                                    "today_clicks",
                                    it.data.today_clicks.toString()
                                ),
                                TopItemsModel(
                                    "top_location",
                                    it.data.top_location
                                ),
                                TopItemsModel(
                                    "top_source",
                                    it.data.top_source
                                )
                            )
                        )


                        var mapData = ArrayList<Pair<String, Float>>()

                        it.data.data.overall_url_chart.also { graphData ->
                            for (prop in graphData::class.memberProperties) {
                                val propertyName = prop.name
                                val data =
                                    graphData.javaClass.kotlin.memberProperties.firstOrNull() {
                                        it.name == propertyName
                                    }?.get(graphData).toString().toFloat()


                                mapData.add(getMonthFromDate(propertyName) to data)
                            }
                            _lineSet.postValue(mapData)

                        }

                    }

                    is ApiState.Loading -> {

                    }

                    is ApiState.Failure -> {


                    }
                }
            }
        }
       }else{
           Toast.makeText(application,"No Network Connction",Toast.LENGTH_SHORT).show()
       }

    }

    fun setGreeting() {
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = if (timeOfDay >= 0 && timeOfDay < 12) {
            "Good Morning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            "Good Afternoon"
        } else {
            "Good Evening"
        }
        _greeting.postValue(greeting)
    }

    fun getMonthFromDate(date: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDate.parse(date)
            return date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        } else {
            return date
        }


    }

    init {
        getData()
    }
}

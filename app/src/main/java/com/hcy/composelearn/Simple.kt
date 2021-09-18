package com.hcy.composelearn

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.android.parcel.Parcelize

/**
 * @author hcy
 * @date 下午5:00
 * description：
 */
class Simple {
}

@Composable
fun HellowScreen(hellowViewMoudel: HellowViewMoudel = viewModel()) {
//    var name by rememberSaveable { mutableStateOf("") }
//    var name by remember { mutableStateOf("") }
    val name: String by hellowViewMoudel.name.observeAsState("")
    HellowContent(name = name, onNameCahnge = { hellowViewMoudel.onNameChange(it) })
}


@Composable
fun HellowContent(name: String, onNameCahnge: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {


        if (name.isNotEmpty()) {
            Text(
                text = "你好，${name}!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
        }


        OutlinedTextField(value = name, onValueChange = onNameCahnge,
            label = {
                Text(text = "请输入你的名字")
            })

    }
}


@Preview
@Composable
fun TestPreview() {
    HellowScreen(viewModel())
}


class HellowViewMoudel : ViewModel() {
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }

}
//
//@Parcelize
//data class City(val name: String, val country: String) : Parcelable

data class City(val name: String, val country: String)



val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = {
            mapOf(
                nameKey to it.name,
                countryKey to it.country
            )
        },
        restore = {
            City(it[nameKey] as String,it[countryKey] as String)
        }
    )
}

val CitySaver2 = listSaver<City,Any>(
    restore = {
        City(it[0] as String,it[1] as String)
    },
    save = {
        listOf(it.name,it.country)
    }
)


@Composable
fun CityScreen(){
    val selectedCity = rememberSaveable( stateSaver = CitySaver) {
        mutableStateOf(City("Madrid","Spain"))
    }
}

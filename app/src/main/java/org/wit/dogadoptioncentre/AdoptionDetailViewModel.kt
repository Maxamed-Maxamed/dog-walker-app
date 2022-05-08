package org.wit.dogadoptioncentre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.dogadoptioncentre.firebase.FirebaseDBManager
import org.wit.dogadoptioncentre.models.AdoptionManager
import org.wit.dogadoptioncentre.models.AdoptionModel
import timber.log.Timber
import java.lang.Exception

class AdoptionDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    private val adoption = MutableLiveData<AdoptionModel>()

    val observableAdoption: LiveData<AdoptionModel>
        get() = adoption

//    fun getAdoption(id: Long) {
//        adoption.value = AdoptionManager.findById(id)
//    }

    fun getAdoption(userid:String, id: String) {
        try {
            //DonationManager.findById(email, id, donation)
            FirebaseDBManager.findById(userid, id, adoption)
            Timber.i("Detail getAdoption() Success : ${
                adoption.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getAdoption() Error : $e.message")
        }
    }
}
package org.wit.dogadoptioncentre.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.dogadoptioncentre.firebase.FirebaseDBManager
import org.wit.dogadoptioncentre.models.AdoptionManager
import org.wit.dogadoptioncentre.models.AdoptionModel
import timber.log.Timber

class AdoptionViewModelContent: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status




    fun addAdoption(firebaseUser: MutableLiveData<FirebaseUser>,
                    adoptions: AdoptionModel) {
        status.value = try {
            //adoptionManager.create(adoption)
            FirebaseDBManager.create(firebaseUser,adoptions)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

//    fun getAdoptions(userid:String, id: String) {
//        try {
//            //adoptionManager.findById(email, id, adoption)
//            FirebaseDBManager.findById(userid, id, )
//            Timber.i("Detail getadoption() Success : ${
//                adoption.value.toString()}")
//        }
//        catch (e: Exception) {
//            Timber.i("Detail getadoption() Error : $e.message")
//        }
//    }











}
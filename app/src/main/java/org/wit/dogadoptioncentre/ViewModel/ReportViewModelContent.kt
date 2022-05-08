package org.wit.dogadoptioncentre.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.wit.dogadoptioncentre.firebase.FirebaseDBManager
import org.wit.dogadoptioncentre.models.AdoptionManager
import org.wit.dogadoptioncentre.models.AdoptionModel
import timber.log.Timber
import java.lang.Exception

class ReportViewModelContent: ViewModel() {

    private val adoptionsList = MutableLiveData<List<AdoptionModel>>()

    val observableAdoptionsList: LiveData<List<AdoptionModel>>
        get() = adoptionsList
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()
    var userFB = FirebaseAuth.getInstance().currentUser
    init {
        load()
    }

//    fun load() {
//        adoptionsList.value = AdoptionManager.findAll()
//    }
    fun load() {
        try {

            FirebaseDBManager.findAll(userFB?.uid!!,
                adoptionsList)
            Timber.i("Report Load Success : ${adoptionsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }


    fun delete(userid: String, id: String) {
        try {

            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
    fun updateAdoption(userid:String, id: String,adoptions: AdoptionModel) {
        try {
            //adoptionManager.update(email, id, adoption)
            FirebaseDBManager.update(userid, id, adoptions)
            Timber.i("Detail update() Success : $adoptions")
        } catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }

    }
}
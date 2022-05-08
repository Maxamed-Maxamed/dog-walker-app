package org.wit.dogadoptioncentre.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import org.wit.dogadoptioncentre.firebase.FirebaseAuthManager


class LoggedInViewModel(app: Application) : AndroidViewModel(app) {


    var firebaseAuthManager : FirebaseAuthManager = FirebaseAuthManager(app)
    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser
    var loggedOut : MutableLiveData<Boolean> = firebaseAuthManager.loggedOut




    fun logOut() {
        firebaseAuthManager.logOut()
    }
}
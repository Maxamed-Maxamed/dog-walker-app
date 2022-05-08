package org.wit.dogadoptioncentre.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface AdoptionStore {
//    fun findAll() : List<AdoptionModel>
//    fun findById(id:Long) : AdoptionModel?
//    fun create(create_adoption : AdoptionModel)

    fun findAll(adoptionsList:
                MutableLiveData<List<AdoptionModel>>
    )
    fun findAll(userid:String,
                adoptionsList:
                MutableLiveData<List<AdoptionModel>>)
    fun findById(userid:String, adoptionId: String,
                 adoptions: MutableLiveData<AdoptionModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, adoptions: AdoptionModel)
    fun delete(userid:String, adoptionsid: String)
    fun update(userid:String, adoptionsid: String, adoptions: AdoptionModel)


}
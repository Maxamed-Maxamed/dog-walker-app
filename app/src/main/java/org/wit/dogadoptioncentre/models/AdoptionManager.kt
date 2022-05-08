package org.wit.dogadoptioncentre.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.wit.dogadoptioncentre.firebase.FirebaseDBManager
import timber.log.Timber


var lastId = 0L
internal fun getId(): Long{
    return lastId++
}

object  AdoptionManager : AdoptionStore {
    val adoption_Array = ArrayList<AdoptionModel>()

//    override fun findAll(): List<AdoptionModel> {
//        return adoption_Array
//    }
//
//    override fun findById(id: Long): AdoptionModel? {
//        return adoption_Array.find { it.id == id }
//    }
//
//    override fun create(create_adoption: AdoptionModel) {
//
//        create_adoption.id = getId()
//        adoption_Array.add(create_adoption)
//        logAll()
//
//    }


  fun  logAll() {
    Timber.v("** Adoption's List **")
    adoption_Array.forEach { Timber.v("ADOPTION ${it}") }
}

    override fun findAll(adoptionsList: MutableLiveData<List<AdoptionModel>>) {
        TODO("Not yet implemented")
    }

//    override fun findAll(adoptionsList: MutableLiveData<List<AdoptionModel>>) {
//        TODO("Not yet implemented")
//    }

//    override fun findAll(userid: String, adoptionsList: MutableLiveData<List<AdoptionModel>>) {
//        TODO("Not yet implemented")
//    }
    override fun findAll(userid: String, adoptionsList: MutableLiveData<List<AdoptionModel>>) {

        FirebaseDBManager.database.child("user-adoptions").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase adoption error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<AdoptionModel>()
                    val children = snapshot.children
                    children.forEach {
                        val adoption = it.getValue(AdoptionModel::class.java)
                        localList.add(adoption!!)
                    }
                    FirebaseDBManager.database.child("user-adoptions").child(userid)
                        .removeEventListener(this)

                    adoptionsList.value = localList
                }
            })
    }
//    override fun findById(
//        userid: String,
//        adoptionId: String,
//        adoptions: MutableLiveData<AdoptionModel>
//    ) {
//        TODO("Not yet implemented")
//    }
    override fun findById( userid: String,  adoptionId: String, adoptions: MutableLiveData<AdoptionModel>) {

    FirebaseDBManager.database.child("user-adoptions").child(userid)
        .child(adoptionId).get().addOnSuccessListener {
            adoptions.value = it.getValue(AdoptionModel::class.java)
            Timber.i("firebase Got value ${it.value}")
        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }
}

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, adoptions: AdoptionModel) {
        Timber.i("Firebase DB Reference : ${FirebaseDBManager.database}")

        val uid = firebaseUser.value!!.uid
        val key = FirebaseDBManager.database.child("adoptions").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        adoptions.uid = key
        val adoptionsValues = adoptions.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/adoptions/$key"] = adoptionsValues
        childAdd["/user-adoptions/$uid/$key"] = adoptionsValues

        FirebaseDBManager.database.updateChildren(childAdd)
    }


override fun delete(userid: String, adoptionsid: String) {
    val childDelete : MutableMap<String, Any?> = HashMap()
    childDelete["/adoptions/$adoptionsid"] = null
    childDelete["/user-adoptions/$userid/$adoptionsid"] = null

    FirebaseDBManager.database.updateChildren(childDelete)
}


    override fun update(userid: String, adoptionsid: String, adoptions: AdoptionModel) {

        val adoptionValues = adoptions.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["adoptions/$adoptionsid"] = adoptionValues
        childUpdate["user-adoptions/$userid/$adoptionsid"] = adoptionValues

        FirebaseDBManager.database.updateChildren(childUpdate)
    }
}
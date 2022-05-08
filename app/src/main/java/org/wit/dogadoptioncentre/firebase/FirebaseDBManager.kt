package org.wit.dogadoptioncentre.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.dogadoptioncentre.models.AdoptionModel
import org.wit.dogadoptioncentre.models.AdoptionStore
import timber.log.Timber

object FirebaseDBManager : AdoptionStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance("https://dogadoption-1cdc3-default-rtdb.europe-west1.firebasedatabase.app/").getReference()


    override fun findAll(adoptionsList: MutableLiveData<List<AdoptionModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, adoptionsList: MutableLiveData<List<AdoptionModel>>) {

        database.child("user-adoptions").child(userid)
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
                    database.child("user-adoptions").child(userid)
                        .removeEventListener(this)

                    adoptionsList.value = localList
                }
            })
    }

    override fun findById( userid: String,  adoptionId: String, adoptions: MutableLiveData<AdoptionModel>) {

        database.child("user-adoptions").child(userid)
            .child(adoptionId).get().addOnSuccessListener {
                adoptions.value = it.getValue(AdoptionModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, adoptions: AdoptionModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("adoptions").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        adoptions.uid = key
        val adoptionsValues = adoptions.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/adoptions/$key"] = adoptionsValues
        childAdd["/user-adoptions/$uid/$key"] = adoptionsValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, adoptionsid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/adoptions/$adoptionsid"] = null
        childDelete["/user-adoptions/$userid/$adoptionsid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, adoptionsid: String, adoptions: AdoptionModel) {

        val adoptionValues = adoptions.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["adoptions/$adoptionsid"] = adoptionValues
        childUpdate["user-adoptions/$userid/$adoptionsid"] = adoptionValues

        database.updateChildren(childUpdate)
    }


}
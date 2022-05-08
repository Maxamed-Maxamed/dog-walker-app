package org.wit.dogadoptioncentre.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize


@Parcelize
data class AdoptionModel (
    var uid: String? = "",
    var id: Long = 0,
    var dogName: String = "",
    var dogBreed: String = "",
    var dogLocation: String = "",
    var ratingbar : Float = 0.0f
        ) : Parcelable


{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "id" to id,
            "dogName" to dogName,
            "dogBreed" to dogBreed,
            "dogLocation" to dogLocation,
            "ratingbar" to ratingbar,

        )
    }
}
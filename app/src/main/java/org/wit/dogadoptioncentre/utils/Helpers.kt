package org.wit.dogadoptioncentre.utils

import org.wit.dogadoptioncentre.R


import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.FragmentActivity


/**
 * It creates a loader.
 *
 * @param activity The activity that the loader will be displayed in.
 * @return A function that takes an activity and returns an AlertDialog.
 */
fun createLoader(activity: FragmentActivity) : AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true) // 'false' if you want user to wait
        .setView(R.layout.loading)
    var loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.mipmap.ic_launcher_round)

    return loader
}

/**
 * If the loader is not showing, set the title to the message and show the loader.
 *
 * @param loader The loader to show.
 * @param message The message to be displayed in the loader.
 */
fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing) {
        loader.setTitle(message)
        loader.show()
    }
}

/**
 * It hides the loader.
 *
 * @param loader AlertDialog
 */
fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing)
        loader.dismiss()
}

/**
 * This function takes in an activity and displays a toast message to the user.
 *
 * @param activity The activity that is calling the function.
 */
fun serviceUnavailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Adoption Service Unavailable. Try again later",
        Toast.LENGTH_LONG
    ).show()
}

fun serviceAvailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Adoption Contacted Successfully",
        Toast.LENGTH_LONG
    ).show()
}
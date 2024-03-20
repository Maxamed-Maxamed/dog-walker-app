package org.wit.dogadoptioncentre.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import androidx.lifecycle.Observer





import org.wit.dogadoptioncentre.R
import org.wit.dogadoptioncentre.activities.AdoptionHome
import org.wit.dogadoptioncentre.databinding.LoginBinding

    class Login : AppCompatActivity() {

        private lateinit var loginRegisterViewModel : LoginRegisterViewModel
        private lateinit var loginBinding : LoginBinding

        /**
         * It sets up the UI for the login screen.
         *
         * @param savedInstanceState If the activity is being re-initialized after previously being
         * shut down then this Bundle contains the data it most recently supplied in
         * onSaveInstanceState(Bundle). Note: Otherwise it is null.
         */
        /* This is the code that sets up the UI for the login screen. */
        public override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            loginBinding = LoginBinding.inflate(layoutInflater)
            setContentView(loginBinding.root)

            loginBinding.emailSignInButton.setOnClickListener {
                signIn(loginBinding.fieldEmail.text.toString(),
                    loginBinding.fieldPassword.text.toString())
            }
            loginBinding.emailCreateAccountButton.setOnClickListener {
                createAccount(loginBinding.fieldEmail.text.toString(),
                    loginBinding.fieldPassword.text.toString())
            }
        }

        /**
         * The function observes the liveFirebaseUser variable in the loginRegisterViewModel. If the
         * user is not null, the user is redirected to the AdoptionHome activity. If the user is null,
         * the user is redirected to the LoginRegister activity
         */
        public override fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            loginRegisterViewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)
            loginRegisterViewModel.liveFirebaseUser.observe(this, Observer
            { firebaseUser -> if (firebaseUser != null)
                startActivity(Intent(this, AdoptionHome::class.java)) })

            loginRegisterViewModel.firebaseAuthManager.errorStatus.observe(this, Observer
            { status -> checkStatus(status) })
        }

        //Required to exit app from Login Screen - must investigate this further
        /**
         * A function that is called when the back button is pressed.
         */
        override fun onBackPressed() {
            super.onBackPressed()
            Toast.makeText(this,"Click again to Close App...", Toast.LENGTH_SHORT).show()
            finish()
        }

        /**
         * It creates an account for the user.
         *
         * @param email The email address of the user.
         * @param password The password for the new account.
         * @return A boolean value
         */

        private fun createAccount(email: String, password: String) {
            Timber.d("createAccount:$email")
            if (!validateForm()) { return }

            loginRegisterViewModel.register(email,password)
        }

        /**
         * The function `signIn` takes two parameters, `email` and `password`, and if the form is
         * valid, it calls the `login` function in the `LoginRegisterViewModel` class
         *
         * @param email The email address of the user.
         * @param password The password for the account.
         * @return a boolean value.
         */
        private fun signIn(email: String, password: String) {
            Timber.d("signIn:$email")
            if (!validateForm()) { return }

            loginRegisterViewModel.login(email,password)
        }



        /**
         * If the error parameter is true, show a toast message
         *
         * @param error Boolean - true if the authentication failed, false if it succeeded
         */
        private fun checkStatus(error:Boolean) {
            if (error)
                Toast.makeText(this,
                    getString(R.string.auth_failed),
                    Toast.LENGTH_LONG).show()
        }


        /**
         * If the email field is empty, set the error message to "Required." and set valid to false. If
         * the password field is empty, set the error message to "Required." and set valid to false
         *
         * @return A boolean value.
         */
        private fun validateForm(): Boolean {
            var valid = true

            val email = loginBinding.fieldEmail.text.toString()
            if (TextUtils.isEmpty(email)) {
                loginBinding.fieldEmail.error = "Required."
                valid = false
            } else {
                loginBinding.fieldEmail.error = null
            }

            val password = loginBinding.fieldPassword.text.toString()
            if (TextUtils.isEmpty(password)) {
                loginBinding.fieldPassword.error = "Required."
                valid = false
            } else {
                loginBinding.fieldPassword.error = null
            }
            return valid
        }
}
package com.example.newsapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

/**
 * activity responsible for sign up.
 */
class SignupActivity : AppCompatActivity() {

    private var error = false
    private lateinit var auth: FirebaseAuth
    private val TAG = "SignupActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun register(view: View) {
        val firstnameEditText = findViewById<EditText>(R.id.firstname)
        val surnameEditText = findViewById<EditText>(R.id.surname)
        val emailAddressEditText = findViewById<EditText>(R.id.emailAddress)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val rePasswordEditText = findViewById<EditText>(R.id.rePassword)
        val firstname = firstnameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val email = emailAddressEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val rePassword = rePasswordEditText.text.toString().trim()
        validate(firstname, surname, email, password, rePassword)
        if (!error) {
            signup(email, firstname, surname, password, view)
        }
    }

    /**
     * validates inputs
     * @param firstname
     * @param surname
     * @param email
     * @param password
     * @param rePassword
     */
    private fun validate(
        firstname: String, surname: String, email: String, password: String,
        rePassword: String
    ) {
        error = false
        val firstnameError = findViewById<TextView>(R.id.firstname_error)
        val surnameError = findViewById<TextView>(R.id.lastname_error)
        val passwordError = findViewById<TextView>(R.id.password_error)
        val emailAddressError = findViewById<TextView>(R.id.email_error)
        if (isEmpty(firstname)) {
            error = true
            firstnameError.text = "Enter your first name"

        } else {
            firstnameError.text = ""
        }
        if (isEmpty(surname)) {
            error = true
            surnameError.text = "Enter your last name"
        } else {
            surnameError.text = ""
        }
        if (isEmpty(email)) {
            error = true
            emailAddressError.text = "please enter an email Address"
        } else if (!isValidEmail(email)) {
            error = true
            emailAddressError.text = "Invalid email address format"
        } else {
            emailAddressError.text = ""
        }
        if (isEmpty(password) || isEmpty(rePassword)) {
            error = true
            passwordError.text = "Fill in both password fields"
        } else if (password != rePassword) {
            error = true
            passwordError.text = "Passwords do not match"
        } else {
            passwordError.text = ""
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val pat = Pattern.compile(emailRegex)
        if (email == null)
            return false
        return pat.matcher(email).matches()

    }

    /**
     * responsible for sign up user.
     */
    private fun signup(
        email: String,
        firstname: String,
        surname: String,
        password: String,
        view: View
    ) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success.
                    sendVerificationEmail(view, firstname, surname)
                    intent = Intent(this, InterestsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        view, "Authentication failed. Please try again later",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }

                // ...
            }
    }

    /**
     * sends email to verify email address
     */
    private fun sendVerificationEmail(view: View, firstname: String, surname: String) {
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(view, "Verification email sent", Snackbar.LENGTH_LONG)

                    val profileUpdates = userProfileChangeRequest {
                        displayName = "$firstname $surname"

                    }

                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }

                }
            }
    }
}
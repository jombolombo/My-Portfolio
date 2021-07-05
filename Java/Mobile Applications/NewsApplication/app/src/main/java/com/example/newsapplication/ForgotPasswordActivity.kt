package com.example.newsapplication

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

/**
 * class responsible for the forgot password work flow
 */
class ForgotPasswordActivity : AppCompatActivity() {
    private var error = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    /**
     * sends reset email to users email address
     */
    fun resetPassword(view: View) {
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        val doneButton = findViewById<Button>(R.id.Send_email_button)
        progressbar.visibility = View.VISIBLE
        val textEditEmail = findViewById<EditText>(R.id.editTextEmailAddress)
        val email = textEditEmail.text.toString().trim()
        validate(email, view)
        if (!error) {
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(view, "Email sent.", Snackbar.LENGTH_LONG)
                    } else {
                        Snackbar.make(view, "Email not sent.", Snackbar.LENGTH_LONG)
                    }
                    progressbar.visibility = View.GONE
                    finish()
                }
        }
    }

    /**
     * validates input.
     * @param email of user.
     */
    private fun validate(email: String, view: View) {
        error = false
        if (isEmpty(email)) {
            Snackbar.make(view, "Enter email address", Snackbar.LENGTH_LONG)
            error = true
        } else {
            if (!(isValidEmail(email))) {
                error = true
                Snackbar.make(view, "invalid email format", Snackbar.LENGTH_LONG)
            }
        }
    }

    /**
     * checks if email format is valid.
    * @param email entered by user.
     * @return if email is valid or not.
     */
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
}
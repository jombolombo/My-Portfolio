package com.example.newsapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private var error = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }

    /**
     * responsible for login.
     */
    fun loginButtonClick(view: View) {
        val loadingProgressbar = findViewById<ProgressBar>(R.id.progressBar)
        loadingProgressbar.visibility = View.VISIBLE
        val usernameEditText = findViewById<EditText>(R.id.editTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val email = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        validate(email, password, loadingProgressbar, view)
        auth = Firebase.auth
        if (!error) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        loadingProgressbar.visibility = View.GONE
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(view, "invalid email or password", Snackbar.LENGTH_LONG)
                            .show()
                        loadingProgressbar.visibility = View.GONE
                    }
                }
        }
    }

    /**
     * takes users to registration activity.
     */
    fun registerButtonClick(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)


    }


    /**
     * takes users to  ForgotPassword activity.
     */
    fun forgotButtonClick(view: View) {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    /**
     * handles validation.
     */
    private fun validate(email: String, password: String, progressBar: ProgressBar, view: View) {
        error = false
        if (isEmpty(email)) {
            error = true
        }
        if (isEmpty(password)) {
            error = true
        }
        if (error) {
            progressBar.visibility = View.GONE
            val snack = Snackbar.make(view, "Fill in all fields", Snackbar.LENGTH_LONG)
            snack.show()
        }
    }
}
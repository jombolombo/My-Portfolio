package com.example.newsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class InterestsActivity : AppCompatActivity() {
    private var checked = false
    private lateinit var auth: FirebaseAuth
    private val TAG = "interestActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    /**
     * controls business switch.
     */
    fun businessCardClicked(view: View) {
        val businessSwitch = findViewById<SwitchCompat>(R.id.BusinessSwitch)
        checked = businessSwitch.isChecked
        if (checked) {
            businessSwitch.isChecked = !checked
        } else {
            businessSwitch.isChecked = !checked
        }
    }

    /**
     * controls entertainment switch.
     */
    fun entertainmentCardClicked(view: View) {
        val entertainmentSwitch = findViewById<SwitchCompat>(R.id.entertainmentSwitch)
        checked = entertainmentSwitch.isChecked
        if (checked) {
            entertainmentSwitch.isChecked = !checked
        } else {
            entertainmentSwitch.isChecked = !checked
        }
    }

    /**
     * controls general switch.
     */
    fun generalCardClicked(view: View) {
        val generalSwitch = findViewById<SwitchCompat>(R.id.generalSwitch)
        checked = generalSwitch.isChecked
        if (checked) {
            generalSwitch.isChecked = !checked
        } else {
            generalSwitch.isChecked = !checked
        }
    }

    /**
     * controls health switch.
     */
    fun healthCardClicked(view: View) {
        val healthSwitch = findViewById<SwitchCompat>(R.id.healthSwitch)
        checked = healthSwitch.isChecked
        if (checked) {
            healthSwitch.isChecked = !checked
        } else {
            healthSwitch.isChecked = !checked

        }
    }

    /**
     * controls science switch.
     */
    fun scienceCardClicked(view: View) {
        val scienceSwitch = findViewById<SwitchCompat>(R.id.scienceSwitch)
        checked = scienceSwitch.isChecked
        if (checked) {
            scienceSwitch.isChecked = !checked
        } else {
            scienceSwitch.isChecked = !checked
        }
    }

    /**
     * controls sport switch.
     */
    fun sportCardClicked(view: View) {
        val sportSwitch = findViewById<SwitchCompat>(R.id.sportsSwitch)
        checked = sportSwitch.isChecked
        if (checked) {
            sportSwitch.isChecked = !checked
        } else {
            sportSwitch.isChecked = !checked
        }
    }

    /**
     * controls technology switch.
     */
    fun technologyCardClicked(view: View) {
        val technologySwitch = findViewById<SwitchCompat>(R.id.technologySwitch)
        checked = technologySwitch.isChecked
        if (checked) {
            technologySwitch.isChecked = !checked
        } else {
            technologySwitch.isChecked = !checked
        }
    }

    /**
     * responsible for saving users preferences and accessing different activities.
     */
    fun doneButtonClicked(view: View) {
        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser
        val map = HashMap<String, String>()
        var business: Boolean = findViewById<SwitchCompat>(R.id.BusinessSwitch).isChecked
        var entertainment = findViewById<SwitchCompat>(R.id.entertainmentSwitch).isChecked
        var general = findViewById<SwitchCompat>(R.id.generalSwitch).isChecked
        var health = findViewById<SwitchCompat>(R.id.healthSwitch).isChecked
        var science = findViewById<SwitchCompat>(R.id.scienceSwitch).isChecked
        var sport = findViewById<SwitchCompat>(R.id.sportsSwitch).isChecked
        var technology = findViewById<SwitchCompat>(R.id.technologySwitch).isChecked
        if (business) {
            map["business"] = "subscribed"
        } else {
            map["business"] = "not subscribed"
        }
        if (entertainment) {
            map["entertainment"] = "subscribed"
        } else {
            map["entertainment"] = "not subscribed"
        }
        if (general) {
            map["general"] = "subscribed"
        } else {
            map["general"] = "not subscribed"
        }
        if (health) {
            map["health"] = "subscribed"
        } else {
            map["health"] = "not subscribed"
        }
        if (science) {
            map["science"] = "subscribed"
        } else {
            map["science"] = "not subscribed"
        }
        if (sport) {
            map["sport"] = "subscribed"
        } else {
            map["sport"] = "not subscribed"
        }
        if (technology) {
            map["technology"] = "subscribed"
        } else {
            map["technology"] = "not subscribed"
        }
        if (user != null) {
            val docRef = db.collection("preferences").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        db.collection("preferences").document(user.uid)
                            .delete()
                            .addOnSuccessListener {
                                db.collection("preferences").document(user.uid)
                                    .set(map)
                                    .addOnSuccessListener {
                                        intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error deleting document", e)
                            }
                    } else {
                        db.collection("preferences").document(user.uid)
                            .set(map)
                            .addOnSuccessListener {
                                intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

        }

    }

}
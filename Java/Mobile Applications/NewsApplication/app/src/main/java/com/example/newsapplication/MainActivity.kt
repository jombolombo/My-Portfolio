package com.example.newsapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.adaptors.TabsAdaptor
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * main activity
 */
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        val mToolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(mToolbar)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        val tabTitles = resources.getStringArray(R.array.tabTitles)
        viewPager.adapter = TabsAdaptor(this)
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = tabTitles[0]
                    1 -> tab.text = tabTitles[1]
                    2 -> tab.text = tabTitles[2]
                }
            }).attach()
    }

    /**
     * creates the menu.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar, menu)

        return true
    }

    /**
     * controls actions to be taken after menu item has been clicked.
     * @param item is the clicked menu item.
     * @return boolean to show if action  action has been taken.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sign_out -> {
                signOut()
                return true
            }
            R.id.interests -> {
                intent = Intent(this, InterestsActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * signs user out of account.
     */
    private fun signOut() {
        Firebase.auth.signOut()
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}
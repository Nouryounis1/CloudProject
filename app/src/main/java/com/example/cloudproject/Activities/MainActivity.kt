package com.example.cloudproject.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cloudproject.Fragments.*
import com.example.cloudproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var bmNavView: BottomNavigationView
    lateinit var fab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bmNavView = findViewById(R.id.bmNavView)
        bmNavView.background = null
        bmNavView.menu.getItem(2).isEnabled = false


        val MapFragment = MapFragment()
        val HomeFragment = HomeFragment()
        val SettingsFragment = SettingsFragment()
        val NewsFragment = NewsFragment()
        setCurrentFragment(HomeFragment)
        fab = findViewById(R.id.fab)


        fab.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, AddTopicFragment())
                commit()
            }


        }

        bmNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setCurrentFragment(HomeFragment)

                }
                R.id.map -> {
                    setCurrentFragment(MapFragment)

                }
                R.id.news -> {
                    setCurrentFragment(NewsFragment)


                }
                R.id.settings -> {
                    setCurrentFragment(SettingsFragment)


                }
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }


}
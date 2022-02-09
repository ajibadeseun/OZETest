package com.ozetest.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application = application)
            )[MainViewModel::class.java]



        bottomNavigationView = findViewById(R.id.bottom_navigation)

        loadFragment(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.home_item -> {
                    // Write your code here
                    loadFragment(HomeFragment())
                }
                R.id.favorite_item-> {
                    // Write your code here
                    loadFragment(FavoritesFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }
    fun loadFragment(fragment: Fragment) = run {
        val fragmentTag: String = fragment.javaClass.name
        supportFragmentManager.beginTransaction().replace(R.id.main_container,fragment,fragmentTag).addToBackStack(fragmentTag).commit()
        return@run
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete_item ->{
                viewModel.deleteAllItems()

                true
            }
            else-> super.onOptionsItemSelected(item)
        }


    }
}
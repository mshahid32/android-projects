package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.easytravel.Fragments.AccountFragment
import com.example.easytravel.Fragments.HomeFragment
import com.example.easytravel.Fragments.RouteFragment
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val firstFragment= HomeFragment()
    val secondFragment= RouteFragment()
    val thirdFragment= AccountFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCurrentFragment(firstFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.route->setCurrentFragment(secondFragment)
                R.id.account->setCurrentFragment(thirdFragment)

            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment).addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        if (binding.bottomNavigation.selectedItemId == R.id.home){
            super.onBackPressed()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }else{
            binding.bottomNavigation.selectedItemId = R.id.home
        }

    }
}
package com.example.farmradio

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.farmradio.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var headerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**                      **/
        FirebaseApp.initializeApp(this)
        @SuppressLint("MissingInflatedId", "LocalSuppress") val toolbar: MaterialToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.parseColor("#2E8B57"));        val reference: StorageReference = FirebaseStorage.getInstance().reference

        reference.listAll().addOnSuccessListener { listResult: ListResult ->
            val directoryNames = listResult.prefixes.joinToString(", ") { it.name }
            Toast.makeText(this@MainActivity, "Directories: $directoryNames", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e ->
            Toast.makeText(this@MainActivity, "There was an error while listing directories", Toast.LENGTH_SHORT).show()
        }
        /**    **/
        /**    **/
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val headerView = binding.navView.getHeaderView(0)
        headerText = headerView.findViewById(R.id.appVersion)
        headerView.setBackgroundColor(Color.parseColor("#2E8B57"));
        setSupportActionBar(binding.toolbar)

        try {
            val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
            headerText.text = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.galleryFragment,
                R.id.videosFragment,
                R.id.audioFragment,
                R.id.podcastFragment,
                R.id.aboutUsFragment
            ), binding.drawerLayout
        )
        binding.bottomNav.visibility = View.GONE
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
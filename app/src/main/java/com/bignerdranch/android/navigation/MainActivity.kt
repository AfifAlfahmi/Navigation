package com.bignerdranch.android.navigation

import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var  navView: NavigationView
  private  lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener:NavController.OnDestinationChangedListener

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.nav_host_fragment)

        drawerLayout = findViewById(R.id.drawer_layout)

        navView =  findViewById(R.id.nav_view )

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        navView.bringToFront()
        navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        navView.setNavigationItemSelectedListener{
//            when(it.itemId){
//                R.id.home -> {
//                   // navController.navigate(R.id.action_map_to_home)
//                }
//
//
//                R.id.map -> navController.navigate(R.id.action_home_to_map)
//            }
//            navView.setCheckedItem(R.id.home)
//            drawerLayout.closeDrawer(GravityCompat.START)
//            true
//        }


        //supportActionBar?.hide()

        listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.action_home_to_map){
                supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.design_default_color_primary)))

            }
            else if( destination.id == R.id.action_map_to_home)
            {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.design_default_color_secondary)))

            }
        }




    }
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController= findNavController(R.id.nav_host_fragment)


        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return  super.onOptionsItemSelected(item)
    }

    /*
        * We could use `AppBarConfiguration(nav_view.menu, drawer_layout)` instead, but since the
        * Share and Send items are nested, they won't be treated as top-level destinations.
        */



}


/** Ask the NavController to handle "navigate up" events. */




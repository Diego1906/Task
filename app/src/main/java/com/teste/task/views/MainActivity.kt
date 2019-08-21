package com.teste.task.views

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.teste.task.R
import com.teste.task.business.PriorityBusiness
import com.teste.task.constants.TaskConstants
import com.teste.task.repository.PriorityCacheConstants
import com.teste.task.util.SecurityPreferences
import com.teste.task.util.extensions.startNewActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Instância variáveis
        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()

        startDefaultFragment()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_done -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.COMPLETE)
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.TODO)
            }
            R.id.nav_logout -> {
                handleLogout()
            }
        }

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.frameContent, it).commit()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun startDefaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun handleLogout() {
        // Apagar os dados do usuário que estão salvos no SharedPreferences
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.NAME)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.EMAIL)

        this.startNewActivity(LoginActivity())
    }
}

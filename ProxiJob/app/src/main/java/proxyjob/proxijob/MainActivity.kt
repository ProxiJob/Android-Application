package proxyjob.proxijob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import com.parse.ParseUser
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

import android.view.MenuItem
import proxyjob.proxijob.model.Jobs
import java.util.*


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {
    var Jobs: ArrayList<Jobs>?= null
    var JobHash = HashMap<Jobs, String>()
    protected lateinit var navigationView: BottomNavigationView

    internal val contentViewId: Int = 0

    internal val navigationMenuItemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ParseUser.getCurrentUser() == null)
            startActivity(Intent(this, Login::class.java))
        setContentView(R.layout.activity_main)
        navigationView = findViewById<BottomNavigationView>(R.id.navigation) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this)
        var selectedFragment : Fragment = MapFragment().newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, selectedFragment)
        transaction.commit()

    }
    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
        var selectedFragment : Fragment?= null
        when (item.itemId) {
            R.id.menu_home -> selectedFragment = MapFragment().newInstance()
           // OTHER FRAGMENT
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, selectedFragment)
        transaction.commit()

        return true
    }

    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    internal fun selectBottomNavigationBarItem(itemId: Int) {
        val menu = navigationView.getMenu()
        var i = 0
        val size = menu.size()
        while (i < size) {
            val item = menu.getItem(i)
            val shouldBeChecked = item.getItemId() === itemId
            if (shouldBeChecked) {
                item.setChecked(true)
                break
            }
            i++
        }
    }
}

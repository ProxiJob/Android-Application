package proxyjob.proxijob

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import com.parse.ParseUser
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.view.MenuItem
import proxyjob.proxijob.model.Jobs
import java.util.*


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {
    /**
     * Called when an item in the bottom navigation menu is selected.
     *
     * @param item The selected item
     *
     * @return true to display the item as the selected item and false if the item should not
     * be selected. Consider setting non-selectable items as disabled preemptively to
     * make them appear non-interactive.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    protected lateinit var navigationView: BottomNavigationView

    internal val contentViewId: Int = 0

    internal val navigationMenuItemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ParseUser.getCurrentUser() == null)
        startActivity(Intent(this, Login::class.java))
        //navigationView = findViewById<BottomNavigationView>(R.id.navigation) as BottomNavigationView
        //navigationView.setOnNavigationItemSelectedListener(this)
        if (ParseUser.getCurrentUser() != null) {
                var selectedFragment: Fragment = MapFragment().newInstance()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, selectedFragment)
                transaction.commit()
        }

    }
    /*override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    override fun onPause() {
        super.onPause()
        //overridePendingTransition(0, 0)
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
    }*/
}

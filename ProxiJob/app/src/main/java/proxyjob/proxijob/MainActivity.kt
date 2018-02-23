package proxyjob.proxijob

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import com.parse.ParseUser
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.view.MenuItem
import android.util.TypedValue
import android.support.design.internal.BottomNavigationMenuView
import android.view.View
import proxyjob.proxijob.Client.MapFragment
import proxyjob.proxijob.Client.Missions
import proxyjob.proxijob.Client.Profil
import proxyjob.proxijob.Company.AllMissions
import proxyjob.proxijob.Company.CompanyMissions
import proxyjob.proxijob.Login.Login


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

    protected lateinit var navigationView: BottomNavigationView

    internal val contentViewId: Int = 0

    internal val navigationMenuItemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ParseUser.getCurrentUser() == null)
            startActivity(Intent(this, Login::class.java))
        navigationView = findViewById<BottomNavigationView>(R.id.navigation) as BottomNavigationView
        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().get("business") != true) {
            navigationView.inflateMenu(R.menu.dm_em)
            val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
            for (i in 0 until menuView.getChildCount()) {
                val iconView = menuView.getChildAt(i).findViewById<View>(android.support.design.R.id.icon)
                val layoutParams = iconView.getLayoutParams()
                val displayMetrics = resources.displayMetrics
                layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, displayMetrics).toInt()
                layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, displayMetrics).toInt()
                iconView.setLayoutParams(layoutParams)
            }
                var selectedFragment: Fragment = MapFragment().newInstance()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, selectedFragment)
                transaction.commit()
        }
        else if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().get("business") == true){
            navigationView.inflateMenu(R.menu.ch_em)
            var selectedFragment: Fragment = CompanyMissions().newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, selectedFragment)
            transaction.commit()
        }
        navigationView.setOnNavigationItemSelectedListener(this)


    }
    override fun onStart() {
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
            R.id.menu_home -> selectedFragment = if (ParseUser.getCurrentUser().get("business") == true ) CompanyMissions().newInstance() else MapFragment().newInstance() //null remplacée par la class Contact Entreprise
            R.id.menu_1 -> selectedFragment = if (ParseUser.getCurrentUser().get("business") == true)  AllMissions().newInstance() else Missions().newInstance()
            R.id.menu_2 -> selectedFragment = Profil().newInstance()
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

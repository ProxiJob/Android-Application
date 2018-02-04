package proxyjob.proxijob

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseUser
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    var mBottomNav : BottomNavigationView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DEBUG", ParseUser.getCurrentUser().objectId)
        if (ParseUser.getCurrentUser() == null)
            startActivity(Intent(this, Login::class.java))
        setContentView(R.layout.activity_main)

        mBottomNav = findViewById(R.id.navigation)
        mBottomNav!!.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                Log.i("DEBUG NAVIG", "" + item)
                // handle desired action here
                // One possibility of action is to replace the contents above the nav bar
                // return true if you want the item to be displayed as the selected item
                return true
            }
        })
    }
}

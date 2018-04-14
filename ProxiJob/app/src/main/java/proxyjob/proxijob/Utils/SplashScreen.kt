package proxyjob.proxijob.Utils

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import proxyjob.proxijob.MainActivity
import proxyjob.proxijob.R
import java.lang.Thread.sleep

/**
 * Created by alexandre on 12/04/2018.
 */

class SplashScreen : Activity() {
    var settings: SharedPreferences?= null
    var editor: SharedPreferences.Editor ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit();

        val background = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 2 seconds
                    Thread.sleep((2 * 1000).toLong())

                    // After 5 seconds redirect to another intent
                    if (settings!!.getString("first", "0") == "0") {
                        editor!!.putString("first", "1")
                        editor!!.apply()
                        val i = Intent(baseContext, ChoiceTutorial::class.java)
                        startActivity(i)
                    } else {
                        val i = Intent(baseContext, MainActivity::class.java)
                        startActivity(i)
                    }

                    //Remove activity
                    finish()
                } catch (e: Exception) {
                }

            }
        }
        // start thread
        background.start()
    }

}
package proxyjob.proxijob.Login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import proxyjob.proxijob.Client.SubscribeClient
import proxyjob.proxijob.Company.SubscribeEntreprise
import proxyjob.proxijob.R
import android.widget.Toast
import android.content.pm.PackageManager
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.content.ContextCompat
import android.provider.MediaStore
import android.net.Uri
import android.support.v4.app.ActivityCompat
import java.io.IOException
import android.graphics.Bitmap
import android.widget.ImageView
import com.parse.ParseFile
import android.util.Log
import android.view.View
import com.parse.LogInCallback
import com.parse.ParseUser
import org.jetbrains.anko.alert
import proxyjob.proxijob.MainActivity
import java.io.ByteArrayOutputStream


/**
 * Created by alexandre on 04/02/2018.
 */

class Login : Activity() {


    var ident : EditText?= null
    var pass : EditText?= null
    var login : Button?= null
    var subscribe : Button?= null
    var settings: SharedPreferences?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i("DEBUG", ParseUser.getCurrentUser().objectId)

        setContentView(R.layout.activity_login)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        ident = findViewById(R.id.ident)
        pass = findViewById(R.id.pass)
        login = findViewById(R.id.login_button)
        subscribe = findViewById(R.id.signup_button)
        login!!.setOnClickListener({
            findViewById<com.wang.avi.AVLoadingIndicatorView>(R.id.avi).visibility = View.VISIBLE
            findViewById<com.wang.avi.AVLoadingIndicatorView>(R.id.avi).show()
            Log.i("INFO", "ID : " + ident!!.text + " PASS : " + pass!!.text)
            ParseUser.logInInBackground(ident!!.text.toString(), pass!!.text.toString(), LogInCallback { user, e ->
                if (user != null)
                {
                        startActivity(Intent(this@Login, MainActivity::class.java))
                } else {
                    alert( e.message.toString()) {
                        findViewById<com.wang.avi.AVLoadingIndicatorView>(R.id.avi).hide()
                    }.show()
                }
            })
        })

        subscribe!!.setOnClickListener({
            var choice = settings!!.getString("choice", "0")
            when (choice) {
                "0" -> startActivity(Intent(this, Subscribe::class.java))
                "1" -> startActivity(Intent(this, SubscribeClient::class.java))
                "2" -> startActivity(Intent(this, SubscribeEntreprise::class.java))
           }

        })

    }

}
package proxyjob.proxijob

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.parse.LogInCallback
import com.parse.ParseUser
import org.jetbrains.anko.alert

/**
 * Created by alexandre on 04/02/2018.
 */

class Login : AppCompatActivity() {
    var ident : EditText?= null
    var pass : EditText?= null
    var login : Button?= null
    var subscribe : Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DEBUG", ParseUser.getCurrentUser().objectId)

        setContentView(R.layout.activity_login)

        ident = findViewById(R.id.ident)
        pass = findViewById(R.id.pass)
        login = findViewById(R.id.login_button)
        subscribe = findViewById(R.id.signup_button)
        login!!.setOnClickListener({
            Log.i("INFO", "ID : " + ident!!.text + " PASS : " + pass!!.text)
            ParseUser.logInInBackground(ident!!.text.toString(), pass!!.text.toString(), LogInCallback { user, e ->
                if (user != null)
                {
                    alert( "CONNECTION ... OKAY") {
                        startActivity(Intent(this@Login, Login::class.java))
                    }.show()
                } else {
                    alert( e.message.toString()) {

                    }.show()
                }
            })
        })

        subscribe!!.setOnClickListener({
            startActivity(Intent(this, Subscribe::class.java))
        })

    }
}
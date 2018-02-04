package proxyjob.proxyjob

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity() {

    var ident : EditText?= null
    var pass : EditText?= null
    var login : Button?= null
    var subscribe : Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

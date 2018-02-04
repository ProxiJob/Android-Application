package proxyjob.proxijob

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

/**
 * Created by alexandre on 04/02/2018.
 */

class Subscribe : AppCompatActivity() {

    var entreprise : Button?= null
    var demandeur : Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_choice)

        entreprise = findViewById(R.id.entreprise)
        demandeur  = findViewById(R.id.client)

        entreprise!!.setOnClickListener {
            startActivity(Intent(this, SubscribeEntreprise::class.java))
        }

        demandeur!!.setOnClickListener {
            startActivity(Intent(this, SubscribeClient::class.java))
        }
        /* login!!.setOnClickListener({
            val user = ParseUser()
            user.username = ident!!.getText().toString()
            if (pass1!!.text.toString() == pass2!!.text.toString())
            {
                user.setPassword(pass1!!.getText().toString())
                user.signUpInBackground(object : SignUpCallback {
                override fun done(e: ParseException?) {
                    if (e == null) {
                        alert( "INSCRIPTION ... OKAY") {

                        }.show()
                    } else {
                        alert( e.message.toString()) {

                        }.show()
                    }
                }
            })
            }
        })

        back!!.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        }) */

    }


}
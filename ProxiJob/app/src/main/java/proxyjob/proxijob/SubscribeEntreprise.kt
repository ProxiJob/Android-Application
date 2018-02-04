package proxyjob.proxijob

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.parse.ParseException
import com.parse.SignUpCallback
import kotlinx.android.synthetic.main.activity_subscribe_entreprise.*
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.KUser

/**
 * Created by alexandre on 04/02/2018.
 */

class SubscribeEntreprise : AppCompatActivity() {
    var entreprise : EditText?= null
    var nb_serie : EditText?= null
    var email : EditText?= null
    var password : EditText?= null
    var phone : EditText?= null
    var subscribe : Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_entreprise)
        entreprise = findViewById(R.id.entreprise)
        nb_serie = findViewById(R.id.nb_siret)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        phone = findViewById(R.id.phone)
        subscribe = findViewById(R.id.subscribe)

        subscribe!!.setOnClickListener {
            if (entreprise!!.text.toString() != "" && nb_serie!!.text.toString() != "" &&
                    email!!.text.toString() != "" && password!!.text.toString() != "" &&
                    phone!!.text.toString() != "") {
                val user = KUser()
                user.username = email!!.getText().toString()
                user.setPassword(password!!.getText().toString())
                user.business = true
                user.email = user.username
                user.phoneNumber = phone!!.text.toString()
                user.siret = nb_siret.text.toString()
                user.signUpInBackground(object : SignUpCallback {
                        override fun done(e: ParseException?) {
                            if (e == null) {
                                alert( "INSCRIPTION ... OKAY") {
                                    startActivity(Intent(this@SubscribeEntreprise, Login::class.java))

                                }.show()
                            } else {
                                alert( e.message.toString()) {

                                }.show()
                            }
                        }
                    })
            } else {
                alert("Merci de remplir toutes les informations demandées") {}.show()
            }
        }
    }
}
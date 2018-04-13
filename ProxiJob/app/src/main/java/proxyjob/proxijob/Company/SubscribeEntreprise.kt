package proxyjob.proxijob.Company

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.parse.ParseException
import com.parse.SignUpCallback
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.KUser
import android.location.Geocoder
import android.util.Log
import proxyjob.proxijob.model.Localisation
import java.util.*
import com.parse.ParseGeoPoint
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R

/**
 * Created by alexandre on 04/02/2018.
 */

class SubscribeEntreprise : Activity() {
    var entreprise : EditText?= null
    var nb_serie : EditText?= null
    var email : EditText?= null
    var password : EditText?= null
    var phone : EditText?= null
    var address : EditText?= null
    var subscribe : Button?=null
    var geocoder: Geocoder?= null
    var addresses: List<Address>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_entreprise)
        entreprise = findViewById(R.id.entreprise)
        nb_serie = findViewById(R.id.nb_siret)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        phone = findViewById(R.id.phone)
        subscribe = findViewById(R.id.subscribe)
        address = findViewById(R.id.address)
        geocoder = Geocoder(this, Locale.getDefault())
        subscribe!!.setOnClickListener {
            if (entreprise!!.text.toString() != "" && nb_serie!!.text.toString() != "" &&
                    email!!.text.toString() != "" && password!!.text.toString() != "" &&
                    phone!!.text.toString() != "" && address!!.text.toString() != "") {
                val user = KUser()
                val company = Company()
                val location = Localisation()
                user.username = email!!.getText().toString()
                user.setPassword(password!!.getText().toString())
                user.business = true
                user.email = user.username
                user.phoneNumber = phone!!.text.toString()
                company.siret = nb_serie!!.text.toString()
                company.name = entreprise!!.text.toString()
                location.address = address!!.text.toString()
                addresses = geocoder!!.getFromLocationName(address!!.text.toString().toLowerCase(), 1);
                for (address in addresses!!) {
                    var outputAddress = ""
                    for (i in 0 until address.getMaxAddressLineIndex()) {
                        outputAddress += " --- " + address.getAddressLine(i)
                    }
                }
                if ( addresses != null) {
                    val address = addresses!!.get(0)
                    val addressFragments = ArrayList<String>()

                    for (i in 0 until address.maxAddressLineIndex) {
                        addressFragments.add(address.getAddressLine(i))
                    }
                    Log.i("DEBUG ADDRESS", "" + address)
                    location.localisation = ParseGeoPoint(address.latitude, address.longitude)
                }
                location.saveInBackground {
                    company.localisation = location
                    company.saveInBackground {
                        user.put("company", company)
                        user.signUpInBackground(object : SignUpCallback {
                            override fun done(e: ParseException?) {
                                if (e == null) {
                                    alert("INSCRIPTION ... OKAY") {
                                        startActivity(Intent(this@SubscribeEntreprise, Login::class.java))

                                    }.show()
                                } else {
                                    alert(e.message.toString()) {

                                    }.show()
                                }
                            }
                        })
                    }
                }

            } else {
                alert("Merci de remplir toutes les informations demandées") {}.show()
            }
        }
    }
}
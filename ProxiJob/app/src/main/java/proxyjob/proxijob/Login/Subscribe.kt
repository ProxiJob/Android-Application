package proxyjob.proxijob.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import proxyjob.proxijob.Client.SubscribeClient
import proxyjob.proxijob.Company.SubscribeEntreprise
import proxyjob.proxijob.R

/**
 * Created by alexandre on 04/02/2018.
 */

class Subscribe : Activity() {

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

    }


}
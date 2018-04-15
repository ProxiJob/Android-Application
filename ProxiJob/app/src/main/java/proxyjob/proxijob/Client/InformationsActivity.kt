package proxyjob.proxijob.Client

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.parse.ParseCloud
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.model.KUser
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexandre on 15/03/18.
 */


class InformationsActivity: Activity()
{
    var first_name : EditText?= null
    var last_name : EditText?= null
    var email : EditText?= null
    var date : EditText?= null
    var secu: EditText?= null
    var address: TextView?= null
    var telephone: EditText?= null
    var validation : com.github.clans.fab.FloatingActionButton? = null
    var leave : com.github.clans.fab.FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations_client)
        first_name = findViewById(R.id.lastname)
        last_name = findViewById(R.id.firstname)
        email = findViewById(R.id.email)
        date = findViewById(R.id.birthday)
        secu = findViewById(R.id.nb_secu)
        address = findViewById(R.id.adresse)
        telephone = findViewById(R.id.telephone)
        validation = findViewById(R.id.refresh)
        leave = findViewById(R.id.exit)

        var user = KUser.getCurrentUser()
        var formatter = SimpleDateFormat("MM/dd/YY")

        if (user.firstname != null)
            first_name!!.text = SpannableStringBuilder(user.firstname)
        if (user.lastname != null)
            last_name!!.text = SpannableStringBuilder(user.lastname)
        if (user.email != null)
            email!!.text = SpannableStringBuilder(user.email as String)
        if (user.birthday != null)
            date!!.text = SpannableStringBuilder(formatter.format(user.birthday))
        if (user.phoneNumber != null)
            telephone!!.text = SpannableStringBuilder(user.phoneNumber)
        if (user.secu != null)
            secu!!.text = SpannableStringBuilder(user.secu)
        if (user.address != null)
            address!!.text = SpannableStringBuilder(user.address)
        validation!!.setOnClickListener { saveProfile() }
        leave!!.setOnClickListener{leaveFunc() }

    }

    fun leaveFunc() {
        KUser.logOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    fun saveProfile() {
        KUser.getCurrentUser().firstname = first_name!!.text.toString()
        KUser.getCurrentUser().lastname = last_name!!.text.toString()
        KUser.getCurrentUser().email = email!!.text.toString()
        KUser.getCurrentUser().birthday = Date(date!!.text.toString())
        KUser.getCurrentUser().secu = (secu!!.text.toString())
        KUser.getCurrentUser().phoneNumber = (telephone!!.text.toString())
        KUser.getCurrentUser().saveInBackground()
    }
}
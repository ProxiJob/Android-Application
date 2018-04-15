package proxyjob.proxijob.Client

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.EditText
import com.parse.ParseCloud
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
    var validation : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations_client)
        first_name = findViewById(R.id.lastname)
        last_name = findViewById(R.id.firstname)
        email = findViewById(R.id.mail)
        date = findViewById(R.id.layoutDate)
        validation = findViewById(R.id.edit)

        var user = KUser.getCurrentUser()
        var formatter = SimpleDateFormat("MM/dd/YY")

        first_name!!.text = SpannableStringBuilder(user.firstname)
        last_name!!.text = SpannableStringBuilder(user.lastname)
        email!!.text = SpannableStringBuilder(user.email as String)
        date!!.text = SpannableStringBuilder(formatter.format(user.birthday))
        validation!!.setOnClickListener { saveProfile(user) }
    }

    fun saveProfile(user: KUser) {
        user.firstname = first_name!!.text.toString()
        user.lastname = last_name!!.text.toString()
        user.email = email!!.text.toString()
        user.birthday = Date(date!!.text.toString())
        user.saveInBackground()
    }
}
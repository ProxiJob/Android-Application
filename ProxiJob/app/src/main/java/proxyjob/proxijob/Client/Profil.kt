package proxyjob.proxijob.Client

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.parse.ParseUser
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 23/02/2018.
 */

class Profil : Fragment() {
    init {

    }
    fun newInstance(): Profil {
        return Profil()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_profil, container, false)
        view.findViewById<Button>(R.id.informations).setOnClickListener {
            startActivity(Intent(context, InformationsActivity::class.java))
        }
        return view
    }
}
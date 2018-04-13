package proxyjob.proxijob.Client

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.parse.ParseFile
import com.parse.ParseUser
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.model.Jobs
import java.util.HashMap

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
        managePost()
    }
    fun managePost() {
        println("JE CREER LE CONTRACT")
        val params = HashMap<String, String>()
        params.put("jobId", "GIK8YJoTVx-Y8C81fAZbg")
        ParseCloud.callFunctionInBackground("getPDF", params, FunctionCallback<ParseFile> { id, e ->
            if (e == null) {
                println("NO ERROR")
                println(id)
                // ratings is 4.5
            } else {
                println("ERROR")
            }
        })
        /*ParseCloud.callFunctionInBackground("createPDFAtBlock", params, FunctionCallback<String> { id, e ->
            if (e == null) {
                println("NO ERROR")
                println(id)
                // ratings is 4.5
            } else {
                println("ERROR")
            }
        })*/
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_profil, container, false)
        view.findViewById<Button>(R.id.informations).setOnClickListener {
            startActivity(Intent(context, InformationsActivity::class.java))
        }
        return view
    }
}
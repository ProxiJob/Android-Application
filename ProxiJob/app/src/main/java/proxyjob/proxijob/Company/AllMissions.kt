package proxyjob.proxijob.Company

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import proxyjob.proxijob.R

/**
 * Created by alexandre on 22/02/2018.
 */

class AllMissions : Fragment() {
    var add : Button?= null
    var mod : Button?= null

    fun newInstance(): AllMissions {
        return AllMissions()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    overrgit ide fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_all_missions, container, false)
        add = view.findViewById(R.id.add)
        mod = view.findViewById(R.id.mod)

        add!!.setOnClickListener {
           startActivity(Intent(context, CreateMission::class.java))
        }

        mod!!.setOnClickListener {
            Log.i("DEBUG", "MOD")
        }
        return view
    }
}
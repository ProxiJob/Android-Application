package proxyjob.proxijob.Client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 13/02/2018.
 */

class Missions : Fragment() {
    var jobs :ArrayList<Jobs>?= null
    init {

    }
    fun newInstance(): Missions {
        return Missions()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_missions_fragment, container, false)
        var list = view.findViewById<ListView>(R.id.list_view)
        view.findViewById<TextView>(R.id.info).text = "Mes missions"
        APIManager.getShared().getMissionsForUser { b, error, arrayList ->
            jobs = arrayList
            var listAdapter = MissionListAdapter(activity, jobs!!)
            list.adapter = listAdapter
            Log.i("JDK", System.getProperty("java.class.version"))
        }

        return view
    }
}
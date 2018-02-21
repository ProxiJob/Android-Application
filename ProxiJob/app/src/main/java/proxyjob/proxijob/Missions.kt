package proxyjob.proxijob

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.maps.SupportMapFragment
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser

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
       Log.i("DEBUG", "222")
        Log.i("USER", "" + KUser.getCurrentUser().objectId)
        var view = inflater!!.inflate(R.layout.activity_missions_fragment, container, false)
        var list = view.findViewById<ListView>(R.id.list_view)
        APIManager.getShared().getMissionsForUser { b, error, arrayList ->
            jobs = arrayList
            var listAdapter = MissionListAdapter(activity, jobs!!)
            list.adapter = listAdapter
        }

        return view
    }
}
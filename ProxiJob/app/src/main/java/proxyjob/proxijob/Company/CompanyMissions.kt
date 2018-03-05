package proxyjob.proxijob.Company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 22/02/2018.
 */
class CompanyMissions : Fragment() {
    var jobs :ArrayList<Jobs>?= null
    init {

    }
    fun newInstance(): CompanyMissions {
        return CompanyMissions()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_missions_fragment, container, false)
        var list = view.findViewById<ListView>(R.id.listView)
        view.findViewById<TextView>(R.id.info).text = "Mes annonces"
        APIManager.getShared().getCompany { b, error, arrayList ->
            APIManager.getShared().getMissionsForCompany( arrayList, { b, error, arrayList ->
                jobs = arrayList
                var listAdapter = CompanyMissionListAdapter(activity, jobs!!)
                list.adapter = listAdapter
            })
        }

        return view
    }
}
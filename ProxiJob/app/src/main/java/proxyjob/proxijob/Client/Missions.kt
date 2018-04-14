package proxyjob.proxijob.Client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Jobs
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.support.design.widget.FloatingActionButton
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.parse.FunctionCallback
import com.parse.ParseCloud
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.KUser
import java.util.HashMap
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout



/**
 * Created by alexandre on 13/02/2018.
 */

class Missions : Fragment() {
    var jobs :ArrayList<Jobs>?= null
    var listAdapter: MissionListAdapter?= null
    init {

    }
    fun newInstance(): Missions {
        return Missions()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_missions_fragment, container, false)
        var list = view.findViewById<SwipeMenuListView>(R.id.listView)
        view.findViewById<TextView>(R.id.info).text = "Mes missions"
        view.findViewById<FloatingActionButton>(R.id.add).visibility = View.GONE
            APIManager.getShared().getMissionsForUser { b, error, arrayList ->
                jobs = arrayList
                if (jobs!!.size == 0)
                    view.findViewById<TextView>(R.id.noMissions).visibility = View.VISIBLE
                listAdapter = MissionListAdapter(this!!.activity!!, jobs!!)
                list.adapter = listAdapter
            }
        val creator = SwipeMenuCreator { menu ->
            // create "open" item
            val openItem = SwipeMenuItem(
                    context)
            // set item background

            // set item width
            openItem.width = 170
            // set item title
            openItem.setIcon(R.drawable.eye)
            // set item title fontsize
            openItem.titleSize = 18
            // set item title font color
            //openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)

            // create "delete" item
            val deleteItem = SwipeMenuItem(
                    context)
            // set item background
            //deleteItem.background = ColorDrawable(Color.rgb(0xF9,
                 //   0x3F, 0x25))
            // set item width
            deleteItem.width = 170
            // set a icon
            deleteItem.setIcon(R.drawable.garbage)
            // add to menu
            menu.addMenuItem(deleteItem)
        }

// set creator
        list.setMenuCreator(creator)
        list.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener {
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {
                when (index) {
                    0 -> {
                        var intent = Intent(activity, MapInformationDetails::class.java)
                        intent.putExtra("objectID", jobs!![position].objectId)
                        startActivity(intent)
                    }
                    1 -> {
                        context!!.alert("Êtes-vous sûr de vouloir annuler \nvotre participation à cette événement ?") {
                            //title = "Alert"
                            yesButton {
                                val params = HashMap<String, String>()
                                params.put("jobId", jobs!![position]!!.objectId)
                                params.put("userID", KUser.getCurrentUser().objectId)
                                ParseCloud.callFunctionInBackground("savePostule", params, FunctionCallback<Float> { aFloat, e ->
                                    if (e == null) {
                                        jobs!!.removeAt(position)
                                        listAdapter = MissionListAdapter(activity!!, jobs!!)
                                        list.adapter = listAdapter
                                        if (jobs!!.size == 0)
                                            view.findViewById<TextView>(R.id.noMissions).visibility = View.VISIBLE
                                        //listAdapter!!.notifyDataSetChanged()
                                    }
                                })
                            }
                            noButton {

                            }
                        }.show()
                    }
                }// open
                // delete
                // false : close the menu; true : not close the menu
                return false
            }
        })

        return view
    }
}

package proxyjob.proxijob.Company

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuLayout
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.parse.FunctionCallback
import com.parse.ParseCloud
import org.jetbrains.anko.alert
import proxyjob.proxijob.Client.MapInformationDetails
import proxyjob.proxijob.Client.MissionListAdapter
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.util.HashMap
import android.app.Activity
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener



/**
 * Created by alexandre on 22/02/2018.
 */
class CompanyMissions : Fragment() {
    var jobs :ArrayList<Jobs>?= null
    var listAdapter: CompanyMissionListAdapter?= null
    init {

    }
    fun newInstance(): CompanyMissions {
        return CompanyMissions()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("WrongViewCast")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_missions_fragment, container, false)
        var list = view.findViewById<SwipeMenuListView>(R.id.listView)
        view.findViewById<TextView>(R.id.info).text = "Mes Annonces"

        val add = view.findViewById<FloatingActionButton>(R.id.add)
        add!!.setOnClickListener {
            startActivity(Intent(context, CreateMission::class.java))
        }
        APIManager.getShared().getCompany { b, error, arrayList ->
            APIManager.getShared().getMissionsForCompany( arrayList, { b, error, arrayList ->
                jobs = arrayList
                if (jobs!!.size != 0)
                    view.findViewById<TextView>(R.id.noMissions).visibility = View.GONE
                listAdapter = CompanyMissionListAdapter(activity, jobs!!)
                list.adapter = listAdapter
            })
            val creator = SwipeMenuCreator { menu ->
                // create "open" item
                val assignItem = SwipeMenuItem(
                        context)
                // set item background
                //deleteItem.background = ColorDrawable(Color.rgb(0xF9,
                //   0x3F, 0x25))
                // set item width
                assignItem.width = 170
                // set a icon
                assignItem.setIcon(R.drawable.eye)
                // add to menu
                menu.addMenuItem(assignItem)

                val openItem = SwipeMenuItem(
                        context)
                // set item background

                // set item width
                openItem.width = 170
                // set item title
                openItem.setIcon(R.drawable.edit)
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
                            var intent = Intent(activity, AssignClient::class.java)
                            intent.putExtra("objectID", jobs!![position].objectId)
                            startActivity(intent)
                        }
                        1 -> {
                            var intent = Intent(activity, ModifiedMission::class.java)
                            intent.putExtra("objectID", jobs!![position].objectId)
                            startActivity(intent)
                        }
                        2 -> {
                            context.alert("Êtes-vous sûr de vouloir annuler cet événement ?") {
                                //title = "Alert"
                                yesButton {
                                    jobs!![position].deleteInBackground {  }
                                    jobs!!.removeAt(position)
                                    listAdapter = CompanyMissionListAdapter(activity, jobs!!)
                                    list.adapter = listAdapter
                                    if (jobs!!.size == 0)
                                        view.findViewById<TextView>(R.id.noMissions).visibility = View.VISIBLE
                                    //listAdapter!!.notifyDataSetChanged()
                                   /* val params = HashMap<String, String>()
                                    params.put("jobId", jobs!![position]!!.objectId)
                                    params.put("userID", KUser.getCurrentUser().objectId)
                                    ParseCloud.callFunctionInBackground("savePostule", params, FunctionCallback<Float> { aFloat, e ->
                                        if (e == null) {
                                            jobs!!.removeAt(position)
                                            listAdapter = CompanyMissionListAdapter(activity, jobs!!)
                                            list.adapter = listAdapter
                                            if (jobs!!.size == 0)
                                                view.findViewById<TextView>(R.id.noMissions).visibility = View.VISIBLE
                                            //listAdapter!!.notifyDataSetChanged()
                                        }
                                    })*/
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
        }

        return view
    }
}
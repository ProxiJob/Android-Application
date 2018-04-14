package proxyjob.proxijob.Company

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.squareup.picasso.Picasso
import org.jetbrains.anko.alert
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by alexandre on 07/03/2018.
 */


class AssignClient: Activity() {
    var postule: Boolean = false
    var objectID: String? = null
    var job: Jobs? = null
    var company_name: TextView? = null
    var company_image: ImageView? = null
    var job_start: TextView? = null
    var job_end: TextView? = null
    var job_title: TextView? = null
    var job_cash: TextView? = null
    var job_detail: TextView? = null
    var adapter: CompanyUsersPostAdapter?= null
    var list: SwipeMenuListView?= null
    var clients: ArrayList<KUser>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var formatter = SimpleDateFormat("dd/MM/YY à HH.MM");
        setContentView(R.layout.activity_assign_mission)
        objectID = getIntent().getExtras().getString("objectID")
        company_name = findViewById(R.id.company_name)
        company_image = findViewById(R.id.company_image)
        job_start = findViewById(R.id.job_start)
        job_end = findViewById(R.id.job_end)
        job_title = findViewById(R.id.job_title)
        job_cash = findViewById(R.id.job_cash)
        job_detail = findViewById(R.id.job_detail)
        list = findViewById<SwipeMenuListView>(R.id.listView)

        APIManager.getShared().getJob(objectID!!, { b: Boolean, error: Error?, arrayList: ArrayList<Jobs> ->
            job = arrayList[0]

            if (job != null) {
                job_start!!.text = formatter.format(job!!.dateStart)
                job_end!!.text = formatter.format(job!!.dateEnd)
                Log.i("DEBUG OBJ", job!!.objectId)

                job_title!!.text = job!!.job
                job_cash!!.text = job!!.price + "€ /h"
                job_detail!!.text = job!!.description
                company_name!!.text = job!!.company!!.fetchIfNeeded<Company>().name
                var logo = job!!.company!!.fetchIfNeeded<Company>()?.logo
                if (logo != null)
                    Picasso.with(applicationContext).load(logo!!.url).into(company_image)
                if (job!!.postule!!.size > 0)
                    APIManager.getShared().getUsersPost(job!!.postule!!, { b: Boolean, error: Error?, arrayList: ArrayList<KUser> ->
                        clients = arrayList
                        adapter = CompanyUsersPostAdapter(this, arrayList!!)
                        list!!.adapter = adapter
                    })
                val creator = SwipeMenuCreator { menu ->
                    // create "open" item
                    val assignItem = SwipeMenuItem(
                            applicationContext)
                    val see = SwipeMenuItem(
                            applicationContext)
                    // set item background
                    //deleteItem.background = ColorDrawable(Color.rgb(0xF9,
                    //   0x3F, 0x25))
                    // set item width
                    assignItem.width = 170
                    see.width = 170
                    // set a icon
                    assignItem.setIcon(R.drawable.check)
                    see.setIcon(R.drawable.eye)
                    // add to menu
                    menu.addMenuItem(assignItem)
                    menu.addMenuItem(see)
                }

// set creator
                list!!.setMenuCreator(creator)
                list!!.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener {
                    override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {
                        when (index) {
                            0 -> {
                                if (job!!.status?.length == 0) {
                                    alert("Assignation de mission\n" + "Voulez-vous assigner " + clients!![position].firstname + " " +
                                            clients!![position].lastname + " à ce job ?") {
                                        title = "Voulez-vous assigner " + clients!![position].firstname + " " +
                                                clients!![position].lastname + " à ce sport ?"
                                        yesButton { job!!.client = clients!![position]
                                            job!!.status = "ACCEPTED"
                                            job!!.saveInBackground()
                                            val paramspdf = HashMap<String, String>()
                                            paramspdf.put("jobId", job!!.objectId)
                                            paramspdf.put("companyId", job!!.company!!.fetchIfNeeded<Company>().objectId)
                                            paramspdf.put("userId", clients!![position].objectId)
                                            paramspdf.put("choice", "0")
                                            ParseCloud.callFunctionInBackground("createPDFAtBlock", paramspdf, FunctionCallback<String> { id, e ->
                                                if (e == null) {
                                                    println("NO ERROR")
                                                    println(id)
                                                    // ratings is 4.5
                                                } else {
                                                    println("ERROR")
                                                }
                                            })
                                        }
                                        noButton { }
                                    }.show()

                                } else {
                                    alert("Attention\nVoulez-vous supprimer cette personne de cette mission ?") {
                                        title = "Voulez-vous supprimer cette personne de cette mission ?"
                                        yesButton {
                                            job!!.status = ""
                                            job!!.client = KUser()
                                            job!!.saveInBackground()}
                                        noButton { }
                                    }.show()

                                }

                            }

                        }
                        return false
                    }
                })

            }
        })
    }

    fun managePost() {

        val params = HashMap<String, String>()
        params.put("jobId", job!!.objectId)
        params.put("userID", KUser.getCurrentUser().objectId)
        ParseCloud.callFunctionInBackground("savePostule", params, FunctionCallback<Float> { aFloat, e ->
            if (e == null) {
                // ratings is 4.5
            }
        })
    }
}

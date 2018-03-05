package proxyjob.proxijob.Client

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.Jobs
import java.text.SimpleDateFormat
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.KUser
import java.util.*


/**
 * Created by alexandre on 10/02/2018.
 */

class MapInformationDetails: Activity()
{
    var postule: Boolean= false
    var objectID : String?= null
    var job: Jobs?= null
    var company_name: TextView?= null
    var company_image: ImageView?= null
    var job_start: TextView?= null
    var job_end: TextView?= null
    var job_title: TextView?= null
    var job_cash: TextView?= null
    var job_detail: TextView?= null
    var post: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var formatter = SimpleDateFormat("dd/MM/YY à HH.MM");
        setContentView(R.layout.activity_map_informations_details)
        objectID = getIntent().getExtras().getString("objectID")
        company_name = findViewById(R.id.companyName)
        company_image = findViewById(R.id.company_image)
        job_start = findViewById(R.id.job_start)
        job_end = findViewById(R.id.job_end)
        job_title = findViewById(R.id.job_title)
        job_cash = findViewById(R.id.job_cash)
        job_detail = findViewById(R.id.job_detail)
        post = findViewById(R.id.post)

        post!!.setOnClickListener {
            managePost()
        }
        APIManager.getShared().getJob(objectID!!, { b: Boolean, error: Error?, arrayList: ArrayList<Jobs> ->
            job = arrayList[0]

        if (job != null) {
            job_start!!.text = formatter.format(job!!.dateStart)
            job_end!!.text = formatter.format(job!!.dateEnd)
            Log.i("DEBUG OBJ", job!!.objectId)
            /*var company = (job!!.get("company") as Company).fetchIfNeeded<Company>()
            company_name!!.text = company!!.name*/
            job_title!!.text = job!!.job
            job_cash!!.text = job!!.price + "€ /h"
            job_detail!!.text = job!!.description
            for (g in job!!.postule!!) {
                if ((g as String).contains(KUser.getCurrentUser().objectId)) {
                    post!!.text = "Annuler ma candidature"
                    postule = true
                    break
                }
                else
                    post!!.text = "Postuler"
            }
            arrayList[0]!!.postule!!.add("COUCOUCONNARD")
arrayList[0]!!.saveInBackground {  }
        }
        })
    }
    fun managePost() {
        if (!postule) {
            job!!.postule!!.add(KUser.getCurrentUser().objectId)
            post!!.text = "Annuler ma candidature"
        }
        else {
            job!!.postule!!.remove(KUser.getCurrentUser().objectId)
            post!!.text = "Postuler"
        }
        job!!.saveInBackground({e ->
            if (e != null)
                alert(e.message.toString()){}.show()
        })
    }
}
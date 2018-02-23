package proxyjob.proxijob.Client

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import proxyjob.proxijob.model.Jobs
import java.text.SimpleDateFormat
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import java.util.*


/**
 * Created by alexandre on 10/02/2018.
 */

class MapInformationDetails: Activity()
{
    var objectID : String?= null
    var lieuTV : TextView?= null
    var jobTV : TextView?= null
    var secteurTV : TextView?= null
    var dateTV : TextView?= null
    var priceTV : TextView?= null
    var addressTV : TextView?= null
    var postuleBT : Button?= null
    var job: ArrayList<Jobs>?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var formatter = SimpleDateFormat("dd/MM/YY à HH.MM");
        setContentView(R.layout.activity_map_informations_details)
        lieuTV = findViewById(R.id.lieu)
        jobTV = findViewById(R.id.job)
        secteurTV = findViewById(R.id.secteur)
        dateTV = findViewById(R.id.date)
        priceTV = findViewById(R.id.price)
        postuleBT = findViewById(R.id.postule)
        addressTV = findViewById(R.id.address)
        objectID = getIntent().getExtras().getString("objectID")
        APIManager.getShared().getJob(objectID!!, { b: Boolean, error: Error?, arrayList: ArrayList<Jobs> ->
            job = arrayList

        if (job != null) {
            lieuTV!!.text = "43 RUE LA BALETTE"
            jobTV!!.text = job!![0]!!.job
            secteurTV!!.text = "RESTAURATION"
            dateTV!!.text = (formatter.format(job!![0].dateStart!!) + "\n \t\t\t\tau \n " +
                    formatter.format(job!![0].dateEnd!!))
            priceTV!!.text = job!![0].price

        }
        })
        postuleBT!!.setOnClickListener {
            Log.i("DEBUG", "job" + job!![0].job)
        }
    }
}
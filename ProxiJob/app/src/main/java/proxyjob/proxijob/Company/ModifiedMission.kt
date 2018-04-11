package proxyjob.proxijob.Company

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.squareup.picasso.Picasso
import org.jetbrains.anko.act
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexandre on 22/02/2018.
 */
class ModifiedMission: Activity()
{
    var postule: Boolean= false
    var objectID : String?= null
    var job: Jobs?= null
    var company_name: TextView?= null
    var company_image: ImageView?= null
    var job_start: EditText?= null
    var job_end: EditText?= null
    var job_title: EditText?= null
    var job_cash: EditText?= null
    var job_detail: EditText?= null
    var post: Button?= null
    var myCalendarStart = Calendar.getInstance()
    var myCalendarEnd = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var formatter = SimpleDateFormat("dd/MM/YY");
        setContentView(R.layout.activity_details_mission)
        objectID = getIntent().getExtras().getString("objectID")
        company_name = findViewById(R.id.company_name)
        company_image = findViewById(R.id.company_image)
        job_start = findViewById(R.id.job_start)
        job_end = findViewById(R.id.job_end)
        job_title = findViewById(R.id.job_title)
        job_cash = findViewById(R.id.job_cash)
        job_detail = findViewById(R.id.job_detail)
        post = findViewById(R.id.post)
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendarStart.set(Calendar.YEAR, year)
            myCalendarStart.set(Calendar.MONTH, monthOfYear)
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateStart()
        }
        job_start!!.setOnClickListener({
            DatePickerDialog(this, date, myCalendarStart
                    .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show()

        })
        val date2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendarEnd.set(Calendar.YEAR, year)
            myCalendarEnd.set(Calendar.MONTH, monthOfYear)
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateEnd()
        }
        job_end!!.setOnClickListener({
            DatePickerDialog(this, date2, myCalendarEnd
                    .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                    myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show()

        })
        post!!.setOnClickListener {
            createMission()
        }
        APIManager.getShared().getJob(objectID!!, { b: Boolean, error: Error?, arrayList: ArrayList<Jobs> ->
            job = arrayList[0]

            if (job != null) {
                job_start!!.text = SpannableStringBuilder(formatter.format(job!!.dateStart))
                job_end!!.text = SpannableStringBuilder(formatter.format(job!!.dateEnd))
                Log.i("DEBUG OBJ", job!!.objectId)

                job_title!!.text = SpannableStringBuilder(job!!.job)
                job_cash!!.text = SpannableStringBuilder(job!!.price)
                job_detail!!.text = SpannableStringBuilder(job!!.description)
                company_name!!.text = job!!.company!!.fetchIfNeeded<Company>().name
                var logo = job!!.company!!.fetchIfNeeded<Company>()?.logo
                post!!.text = "Modifier"
                if (logo != null)
                    Picasso.with(applicationContext).load(logo!!.url).into(company_image)
            }
        })
    }

    private fun updateStart() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.FRENCH)

        job_start!!.setText(sdf.format(myCalendarStart.time))
    }
    private fun updateEnd() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.FRENCH)

        job_end!!.setText(sdf.format(myCalendarEnd.time))
    }

    private fun createMission()
    {
        if (job_start!!.text.toString() != "" && job_end!!.text.toString() != "" &&
                job_detail!!.text.toString() != "" && job_cash!!.text.toString() != ""
                && job_title!!.text.toString() != "") {

            job!!.dateStart = Date(job_start!!.text.toString())
            job!!.dateEnd = Date(job_end!!.text.toString())
            job!!.job = job_title!!.text.toString()
            job!!.price = job_cash!!.text.toString()
            job!!.description = job_detail!!.text.toString()
            job!!.company = KUser.getCurrentUser().company
            job!!.saveInBackground({
                finish()
            })
        }
    }
}
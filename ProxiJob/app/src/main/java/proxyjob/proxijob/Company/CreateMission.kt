package proxyjob.proxijob.Company

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.R.id.date
import proxyjob.proxijob.Client.MapFragment
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by alexandre on 22/02/2018.
 */

class CreateMission : Fragment() {
    var myCalendarStart = Calendar.getInstance()
    var myCalendarEnd = Calendar.getInstance()
    var start : EditText?= null
    var end : EditText?= null
    var jobS : EditText?= null
    var desc : EditText?= null
    var price : EditText?= null
    var add : Button?= null
    fun newInstance(): CreateMission {
        return CreateMission()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_create_mission, container, false)
        start = view.findViewById(R.id.startText)
        end = view.findViewById(R.id.endText)
        jobS = view.findViewById(R.id.jobText)
        desc = view.findViewById(R.id.descText)
        price = view.findViewById(R.id.priceText)
        add = view.findViewById(R.id.add)
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendarStart.set(Calendar.YEAR, year)
            myCalendarStart.set(Calendar.MONTH, monthOfYear)
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateStart()
        }
        start!!.setOnClickListener({
            DatePickerDialog(context, date, myCalendarStart
                    .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show()

        })
        val date2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendarEnd.set(Calendar.YEAR, year)
            myCalendarEnd.set(Calendar.MONTH, monthOfYear)
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateEnd()
        }
        end!!.setOnClickListener({
            DatePickerDialog(context, date2, myCalendarEnd
                    .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                    myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show()

        })
        add!!.setOnClickListener {
            createMission(view)
        }
        return view
    }
    private fun updateStart() {
        val myFormat = "dd/MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.FRENCH)

        start!!.setText(sdf.format(myCalendarStart.time))
    }
    private fun updateEnd() {
        val myFormat = "dd/MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.FRENCH)

        end!!.setText(sdf.format(myCalendarEnd.time))
    }

    private fun createMission(view: View)
    {
        if (start!!.text.toString() != "" && end!!.text.toString() != "" &&
                desc!!.text.toString() != "" && price!!.text.toString() != ""
                && jobS!!.text.toString() != "") {
            var job = Jobs()

            job.dateStart = Date(start!!.text.toString())
            job.dateEnd = Date(end!!.text.toString())
            job.job = jobS!!.text.toString()
            job.price = price!!.text.toString()
            job.description = desc!!.text.toString()
            job.company = KUser.getCurrentUser().company
            job.saveInBackground({
                val newGamefragment = CompanyMissions().newInstance()
                val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, newGamefragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            })
        } else {
            Snackbar.make(view, "Hello World", Snackbar.LENGTH_LONG).show();
        }

    }
}
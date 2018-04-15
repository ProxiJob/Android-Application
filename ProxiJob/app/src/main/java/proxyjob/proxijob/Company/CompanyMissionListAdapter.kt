package proxyjob.proxijob.Company

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexandre on 22/02/2018.
 */
class CompanyMissionListAdapter(private var activity: Activity, private var items: ArrayList<Jobs>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtJob: TextView? = null
        var txtStatus: TextView?= null
        var imageAvatar: ImageView?= null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.txtJob = row?.findViewById<TextView>(R.id.job)
            this.imageAvatar = row?.findViewById<ImageView>(R.id.imgAvatar)
            this.txtStatus = row?.findViewById<TextView>(R.id.status)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custom_list_view_item_missions_comp, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var job = items[position]
        var sdf = SimpleDateFormat("yyy-MM-dd")
        var date1 = Date()
        var date2 = job.dateEnd
        if (date1.before(date2) == true)
            viewHolder.txtStatus?.text = "Nouveau !!"
        else
            viewHolder.txtStatus?.text = "Archivé"
        if (job.get("clients") != null || job!!.postule != null && job!!.postule!!.size > 0)
            if (job.get("clients") == null)
                viewHolder.txtName?.text = job!!.postule!!.size.toString() + "" + checkStatusMission(job)
            else
                viewHolder.txtName?.text = checkStatusMission(job)
        else
            viewHolder.txtName?.text = "Mission en attente d'assignation"
        viewHolder.txtJob?.text = job.job
        var logo = job.company!!.fetchIfNeeded<Company>()?.logo
        if (logo != null)
            Picasso.with(activity).load(logo!!.url).into(viewHolder.imageAvatar)
        else
            Picasso.with(activity).load(R.drawable.default_company).into(viewHolder.imageAvatar)
        return view as View
    }

    override fun getItem(i: Int): Jobs {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    fun checkStatusMission(job : Jobs) : String
    {
        var sentence = ""
        var status = job.status
        if (status != null && status != "") {
            var name = job.client?.fetchIfNeeded()?.get("lastName")
            var sex = job.client?.fetchIfNeeded()?.get("sex")
            var lib = if (sex == "Homme") "Mr. " else "Mme "
            when (status) {
                "PENDING" -> sentence = lib + name + " demande à participer au job : " + job.job
                "ACCEPTED" -> sentence = "En attende de signature de contrat par " + lib + name
                "CONTRACTED" -> sentence = "La mission est acceptée avec " + lib + name
                "CANCELED" -> sentence = "Vous avez refuser " + lib + name + "pour le job " + job.job
            }
            if (job.get("contractClient") != null)
                sentence = "La mission est acceptée avec " + lib + name
         } else {
            if (job!!.postule!!.size == 1)
                sentence = " personne a postulé à votre offre."
            else
                sentence = " personnes ont postulé à votre offre"
        }
        return sentence
    }

}
package proxyjob.proxijob.Client

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 21/03/2018.
 */

class RecommendationListAdapter(private var activity: Activity, private var items: ArrayList<Jobs>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var profile_image: CircleImageView?= null
        var profile_image2: CircleImageView?= null
        var profile_image3: CircleImageView?= null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.profile_image = row?.findViewById<CircleImageView>(R.id.profile_image)
            this.profile_image2 = row?.findViewById<CircleImageView>(R.id.profile_image2)
            this.profile_image3 = row?.findViewById<CircleImageView>(R.id.profile_image3)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custom_list_view_item_recommendations, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var job = items[position]
        viewHolder.txtName?.text = checkStatusMission(job)
        var logo = job.company!!.fetchIfNeeded<Company>()?.logo
            Picasso.with(activity).load(logo!!.url).into(viewHolder.profile_image)
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
        var name = job.company?.fetchIfNeeded<Company>()?.name
        var status = job.status

        when (status) {
            null, "" -> sentence = "En attente de réponse."
            "ACCEPTED" -> sentence = name + " à acceptée ! \nVeuillez signer votre contrat."
            "CONTRACTED" -> sentence = "Mission confirmée avec " + name + "."
            "CANCELED" -> sentence = "Votre candidature n'a pas été retenue."
        }
        return sentence
    }
}
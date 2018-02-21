package proxyjob.proxijob

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 13/02/2018.
 */
class MissionListAdapter(private var activity: Activity, private var items: ArrayList<Jobs>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null
        var status: TextView? = null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.txtComment = row?.findViewById<TextView>(R.id.txtComment)
            this.status = row?.findViewById<TextView>(R.id.status_text)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custom_list_view_item_missions, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var job = items[position]
        viewHolder.txtName?.text = job.company?.fetchIfNeeded<Company>()?.name
        viewHolder.txtComment?.text = job.job
        viewHolder.status?.text = if (job.status == "ACCEPTED") "Votre mission est acceptée" else "Votre mission est en cours de traitement"
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
}
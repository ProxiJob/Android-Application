package proxyjob.proxijob.Company

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.parse.ParseFile
import com.squareup.picasso.Picasso
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.util.ArrayList

/**
 * Created by alexandre on 07/03/2018.
 */

class CompanyUsersPostAdapter(private var activity: Activity, private var items: ArrayList<KUser>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var imageAvatar: ImageView?= null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.imageAvatar = row?.findViewById<ImageView>(R.id.imgAvatar)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custom_list_post, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var user = items[position]
        var logo = user.get("profilPicture")
        viewHolder.txtName!!.text = user.lastname + " " + user.firstname
        if (logo != null)
            Picasso.with(activity).load((logo as ParseFile).url).into(viewHolder.imageAvatar)
        else
            Picasso.with(activity).load(R.drawable.default_avatar).into(viewHolder.imageAvatar)
        return view as View
    }

    override fun getItem(i: Int): KUser {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}
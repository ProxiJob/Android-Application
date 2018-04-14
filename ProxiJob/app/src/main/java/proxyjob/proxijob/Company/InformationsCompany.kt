package proxyjob.proxijob.Company

import android.app.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_subscribe.view.*
import kotlinx.android.synthetic.main.fragment_informations_company.view.*
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.KUser
import proxyjob.proxijob.model.Localisation

/**
 * Created by Melvin on 20/03/18.
 */

class InformationsCompany: Fragment()
{
    fun newInstance(): InformationsCompany {
        return InformationsCompany()
    }

    var logo: CircleImageView?=null
    var company_name: EditText?=null
    var company_type: EditText?=null
    var description: EditText?=null
    var siret: EditText?=null
    var telephone: EditText?=null
    var email: EditText?=null
    var adresse: EditText?=null
    var edit: FloatingActionButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_informations_company, container, false)

        logo = view.findViewById<CircleImageView>(R.id.profile_image)
        company_name = view.findViewById(R.id.company_name)
        company_type = view.findViewById<EditText>(R.id.company_type)
        description = view.findViewById<EditText>(R.id.description)
        siret = view.findViewById<EditText>(R.id.nb_siret)
        telephone = view.findViewById<EditText>(R.id.nb_telephone)
        email = view.findViewById<EditText>(R.id.email)
        adresse = view.findViewById<EditText>(R.id.adresse)
        edit = view.findViewById<FloatingActionButton>(R.id.edit)

        var user = KUser.getCurrentUser()
        println(user.objectId)
        company_name!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.name != "") user.company?.fetchIfNeeded<Company>()?.name else "Nom de l'entreprise")
        siret!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.siret != "") user.company?.fetchIfNeeded<Company>()?.siret else "N° Siret")
        company_type!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.secteur != "") user.company?.fetchIfNeeded<Company>()?.secteur else "SECTEUR")
        var des = if (user.company?.fetchIfNeeded<Company>()?.description?.length != 0) user.company?.fetchIfNeeded<Company>()?.description else "D"
        description!!.text = SpannableStringBuilder(if (des != null) des else "Description")
        email!!.text = SpannableStringBuilder(if (user.email != "") user.email else "Email")
        telephone!!.text = SpannableStringBuilder(if (user.phoneNumber != "") user.phoneNumber else "N° Téléphone")
        var oo = if (user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address?.length != 0) user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address else "Adresse"
        println(oo)
        adresse!!.text = SpannableStringBuilder(if (oo != null) oo else "Adresse")
        return view
    }
}
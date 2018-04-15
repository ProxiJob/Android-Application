package proxyjob.proxijob.Company

import android.app.Activity
import android.app.AlertDialog

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
import android.content.DialogInterface
import android.content.Intent
import org.jetbrains.anko.alert
import org.w3c.dom.Text
import proxyjob.proxijob.Login.Login


/**
 * Created by Melvin on 20/03/18.
 */

class InformationsCompany: Fragment()
{
    fun newInstance(): InformationsCompany {
        return InformationsCompany()
    }

    var logo: CircleImageView?=null
    var company_name: TextView?=null
    var company_type: TextView?=null
    var description: TextView?=null
    var siret: TextView?=null
    var telephone: TextView?=null
    var email: TextView?=null
    var adresse: TextView?=null
    var edit: FloatingActionButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_informations_company, container, false)

        logo = view.findViewById<CircleImageView>(R.id.profile_image)
        company_name = view.findViewById(R.id.company_name)
        company_type = view.findViewById<TextView>(R.id.company_type)
        description = view.findViewById<TextView>(R.id.description)
        siret = view.findViewById<TextView>(R.id.nb_siret)
        telephone = view.findViewById<TextView>(R.id.nb_telephone)
        email = view.findViewById<TextView>(R.id.email)
        adresse = view.findViewById<TextView>(R.id.adresse)
        edit = view.findViewById<FloatingActionButton>(R.id.edit)

        var user = KUser.getCurrentUser()
        description!!.setOnClickListener {
            showChangeLangDialog(description!!, description!!.text.toString(), "Description", "Modifier ma description")
        }
        company_name!!.setOnClickListener {
            showChangeLangDialog(company_name!!, company_name!!.text.toString(), "Nom de l'entreprise", "Modifier le nom de l'entreprise")
        }
        company_type!!.setOnClickListener {
            showChangeLangDialog(company_type!!, company_type!!.text.toString(), "Secteur d'activité de l'entreprise", "Modifier mon secteur")
        }
        siret!!.setOnClickListener {
            showChangeLangDialog(siret!!, siret!!.text.toString(), "N° Siret", "Modifier mon N° Siret")
        }
        telephone!!.setOnClickListener {
            showChangeLangDialog(telephone!!, telephone!!.text.toString(), "N° Téléphone", "Modifier mon N° Téléphone")
        }
        email!!.setOnClickListener {
            showChangeLangDialog(email!!, email!!.text.toString(), "Email", "Modifier mon Email")
        }
        adresse!!.setOnClickListener {

        }
        view.findViewById<com.github.clans.fab.FloatingActionButton>(R.id.add).setOnClickListener {
            KUser.logOut()
            startActivity(Intent(activity, Login::class.java))
            activity.finish()
        }
        view.findViewById<com.github.clans.fab.FloatingActionButton>(R.id.refresh).setOnClickListener {
            saveUser()
        }
        company_name!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.name != "") user.company?.fetchIfNeeded<Company>()?.name else "Nom de l'entreprise")
        siret!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.siret != "") user.company?.fetchIfNeeded<Company>()?.siret else "N° Siret")
        company_type!!.text = SpannableStringBuilder(if (user.company?.fetchIfNeeded<Company>()?.secteur != "") user.company?.fetchIfNeeded<Company>()?.secteur else "SECTEUR")

        var des = if (user.company?.fetchIfNeeded<Company>()?.description?.length != 0) user.company?.fetchIfNeeded<Company>()?.description else "D"

        description!!.text = SpannableStringBuilder(if (des != null) des else "Description")
        email!!.text = SpannableStringBuilder(if (user.email != "") user.email else "Email")
        telephone!!.text = SpannableStringBuilder(if (user.phoneNumber != null) user.phoneNumber else "N° Téléphone")

        var oo = if (user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address?.length != 0) user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address else "Adresse"

        adresse!!.text = SpannableStringBuilder(if (oo != null) oo else "Adresse")

        var image = user.company!!.fetchIfNeeded<Company>()?.logo

        if (image != null) {
            Picasso.with(activity).load(image.url).into(logo)
        }

        return view
    }

    fun showChangeLangDialog(view: TextView, old: String, title: String, desc: String) {
        val alertDialog = AlertDialog.Builder(context)
        val input = EditText(context)
        input.text = SpannableStringBuilder(old)
        alertDialog.setTitle(title)
        alertDialog.setMessage(desc)
        alertDialog.setView(input)
        alertDialog.setCancelable(false)
                .setPositiveButton("Enregistrer") { dialog, id ->
                    println(input.text.toString())
                    view.text = input.text.toString()}
                .setNegativeButton("Annuler"
                ) { dialog, id -> dialog.cancel() }

        // create an alert dialog
        val alert = alertDialog.create()
        alertDialog.show()
    }
    fun saveUser()
    {
        println("JE SAVE")
        KUser.getCurrentUser().company?.fetchIfNeeded<Company>()?.name = company_name!!.text.toString()
        KUser.getCurrentUser() .company?.fetchIfNeeded<Company>()?.siret = siret!!.text.toString()
        KUser.getCurrentUser().company?.fetchIfNeeded<Company>()?.secteur = company_type!!.text.toString()
        KUser.getCurrentUser().company?.fetchIfNeeded<Company>()?.description = description!!.text.toString()
        KUser.getCurrentUser().email = email!!.text.toString()
        KUser.getCurrentUser().phoneNumber = telephone!!.text.toString()
        KUser.getCurrentUser().saveInBackground()
        activity.alert {
            message("Votre profil a été correctement sauvegardé")
        }
        //var oo = if (user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address?.length != 0) user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address else "Adresse"

        //adresse!!.text = SpannableStringBuilder(if (oo != null) oo else "Adresse")
    }
}
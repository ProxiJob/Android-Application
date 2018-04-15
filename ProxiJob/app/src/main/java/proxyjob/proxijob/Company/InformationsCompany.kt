package proxyjob.proxijob.Company

import android.Manifest
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
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.codemybrainsout.placesearch.PlaceSearchDialog
import com.parse.ParseFile
import com.parse.ParseGeoPoint
import kotlinx.android.synthetic.main.fragment_informations_company.*
import org.jetbrains.anko.alert
import org.w3c.dom.Text
import proxyjob.proxijob.Login.Login
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


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
    var geocoder: Geocoder?= null
    var addresses: List<Address>? = null
    var changedAdress = false
    var changedPhoto = false
    var settings: SharedPreferences?= null
    var editor: SharedPreferences.Editor ?= null
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    var photoIsAdd = false
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
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
        geocoder = Geocoder(activity, Locale.getDefault())
        settings = context.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit()

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
        var placeSearchDialog =  PlaceSearchDialog.Builder(activity)
                .setLocationNameListener { locationName ->
                    adresse!!.text = locationName
                    changedAdress = true
                }.build()
        adresse!!.setOnClickListener {
                placeSearchDialog.show()
        }
        view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image).setOnClickListener {
            showFileChooser()
            changedPhoto = true
        }
        view.findViewById<com.github.clans.fab.FloatingActionButton>(R.id.add).setOnClickListener {
            editor!!.putString("choice", "0")
            editor!!.apply()
            KUser.logOut()
            var intent = Intent(activity, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
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
        } else
            Picasso.with(activity).load(R.drawable.default_company).into(logo)

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
        if (changedAdress) {
            KUser.getCurrentUser().company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address = adresse!!.text.toString()
            addresses = ArrayList()
            addresses = geocoder!!.getFromLocationName(adresse!!.text.toString().toLowerCase(), 1);
            for (address in addresses!!) {
                var outputAddress = ""
                for (i in 0 until address.getMaxAddressLineIndex()) {
                    outputAddress += " --- " + address.getAddressLine(i)
                }
            }
            if (addresses != null) {
                val address = addresses!!.get(0)
                val addressFragments = ArrayList<String>()

                for (i in 0 until address.maxAddressLineIndex) {
                    addressFragments.add(address.getAddressLine(i))
                }
                Log.i("DEBUG ADDRESS", "" + address)
                KUser.getCurrentUser().company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.localisation = ParseGeoPoint(address.latitude, address.longitude)
            }
        }
        if (changedPhoto)
        {
            KUser.getCurrentUser().company!!.fetchIfNeeded<Company>()?.logo = conversionBitmapParseFile(bitmap!!)
            KUser.getCurrentUser().saveInBackground()
        }
        KUser.getCurrentUser().saveInBackground()
        activity.alert {
            message("Votre profil a été correctement sauvegardé")
        }
        //var oo = if (user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address?.length != 0) user.company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.address else "Adresse"

        //adresse!!.text = SpannableStringBuilder(if (oo != null) oo else "Adresse")
    }
    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    fun conversionBitmapParseFile(imageBitmap: Bitmap): ParseFile {
        val byteArrayOutputStream = ByteArrayOutputStream()
        var maxSize = 100
        var outWidth = 0
        var outHeight = 0
        var inWidth = imageBitmap.getWidth();
        var inHeight = imageBitmap.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        var resizeBitmap = Bitmap.createScaledBitmap(imageBitmap, outWidth, outHeight, false)
        var newBitmap = Bitmap.createBitmap(100, 100, resizeBitmap.getConfig())
        var centreX = (100  - resizeBitmap.width) /2
        var centreY = (100 - resizeBitmap.height) /2
        val canvas = Canvas(newBitmap)
        canvas.drawColor(resources.getColor(R.color.photo_background))
        canvas.drawBitmap(resizeBitmap, centreX.toFloat(), centreY.toFloat(), null)
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageByte = byteArrayOutputStream.toByteArray()
        return ParseFile("image_file.png", imageByte)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, filePath)
                view!!.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image).setImageBitmap(bitmap)
                photoIsAdd = true
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    //method to get the file path from uri
    fun getPath(uri: Uri): String {
        var cursor = activity.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()

        cursor = activity.contentResolver.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()

        return path
    }


    //Requesting permission


    //This method will be called when the user will tap on allow or deny
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //Checking the request code of our request
        if (requestCode == 123) {

            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                //context.Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                //Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }
    }
}
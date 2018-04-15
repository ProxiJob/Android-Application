package proxyjob.proxijob.Client

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.codemybrainsout.placesearch.PlaceSearchDialog
import com.parse.ParseCloud
import com.parse.ParseFile
import com.parse.ParseGeoPoint
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.KUser
import proxyjob.proxijob.model.Localisation
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexandre on 15/03/18.
 */


class InformationsActivity: Activity()
{
    var first_name : EditText?= null
    var last_name : EditText?= null
    var email : EditText?= null
    var date : EditText?= null
    var secu: EditText?= null
    var address: TextView?= null
    var telephone: EditText?= null
    var validation : com.github.clans.fab.FloatingActionButton? = null
    var leave : com.github.clans.fab.FloatingActionButton? = null
    var photo: CircleImageView?=null
    var geocoder: Geocoder?= null
    var addresses: List<Address>? = null
    var changedAdress = false
    var changedPhoto = false
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    var photoIsAdd = false
    var settings: SharedPreferences?= null
    var editor: SharedPreferences.Editor ?= null
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations_client)
        first_name = findViewById(R.id.firstname)
        last_name = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        date = findViewById(R.id.birthday)
        secu = findViewById(R.id.nb_secu)
        address = findViewById(R.id.adresse)
        telephone = findViewById(R.id.telephone)
        validation = findViewById(R.id.refresh)
        leave = findViewById(R.id.exit)
        photo = findViewById(R.id.profile_image)
        geocoder = Geocoder(this, Locale.getDefault())
        var user = KUser.getCurrentUser()
        var formatter = SimpleDateFormat("MM/dd/YY")
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit()
        if (user.get("profilPicture") != null)
            Picasso.with(this).load(user.profilPicture!!.url).into(photo)
        else
            Picasso.with(this).load(R.drawable.default_avatar).into(photo)
        if (user.firstname != null && user.firstname != "")
            first_name!!.text = SpannableStringBuilder(user.firstname)
        else {
            first_name!!.text = SpannableStringBuilder("Non renseigné")
            println("OUIIII")
        }
        if (user.lastname != null && user.lastname != "")
            last_name!!.text = SpannableStringBuilder(user.lastname)
        else
            last_name!!.text = SpannableStringBuilder("Non renseigné")
        if (user.email != null && user.email != "")
            email!!.text = SpannableStringBuilder(user.email as String)
        else
            email!!.text = SpannableStringBuilder("Non renseigné")
        if (user.birthday != null && user.birthday.toString() != "")
            date!!.text = SpannableStringBuilder(formatter.format(user.birthday))
        else
            date!!.text = SpannableStringBuilder("Non renseigné")
        if (user.phoneNumber != null && user.phoneNumber != "")
            telephone!!.text = SpannableStringBuilder(user.phoneNumber)
        else
            telephone!!.text = SpannableStringBuilder("Non renseigné")
        if (user.secu != null && user.secu != "")
            secu!!.text = SpannableStringBuilder(user.secu)
        else
            secu!!.text = SpannableStringBuilder("Non renseigné")
        if (user.address != null && user.address != "")
            address!!.text = SpannableStringBuilder(user.address)
        else
            address!!.text = SpannableStringBuilder("Non renseigné")
        var placeSearchDialog =  PlaceSearchDialog.Builder(this)
                .setLocationNameListener { locationName ->
                    address!!.text = locationName
                    changedAdress = true
                }.build()
        photo!!.setOnClickListener {
            showFileChooser()
            changedPhoto = true
        }
        address!!.setOnClickListener {
            placeSearchDialog.show()
        }
        validation!!.setOnClickListener { saveProfile() }
        leave!!.setOnClickListener{leaveFunc() }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun leaveFunc() {
        editor!!.putString("choice", "0")
        editor!!.apply()
        KUser.logOut()

        var intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        this.getFragmentManager().popBackStack()
        finish()
    }

    fun saveProfile() {
        KUser.getCurrentUser().firstname = first_name!!.text.toString()
        KUser.getCurrentUser().lastname = last_name!!.text.toString()
        KUser.getCurrentUser().email = email!!.text.toString()
        KUser.getCurrentUser().birthday = Date(date!!.text.toString())
        KUser.getCurrentUser().secu = (secu!!.text.toString())
        KUser.getCurrentUser().phoneNumber = (telephone!!.text.toString())
        if (changedAdress) {
            KUser.getCurrentUser().address = address!!.text.toString()

        }
        if (changedPhoto)
        {
            KUser.getCurrentUser().profilPicture = conversionBitmapParseFile(bitmap!!)
            KUser.getCurrentUser().saveInBackground()
        }
        KUser.getCurrentUser().saveInBackground()
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
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                photo!!.setImageBitmap(bitmap)
                photoIsAdd = true
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    //method to get the file path from uri
    fun getPath(uri: Uri): String {
        var cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()

        cursor = contentResolver.query(
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
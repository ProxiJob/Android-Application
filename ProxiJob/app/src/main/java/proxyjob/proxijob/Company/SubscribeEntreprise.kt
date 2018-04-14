package proxyjob.proxijob.Company

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.parse.ParseException
import com.parse.SignUpCallback
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.KUser
import android.location.Geocoder
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.CheckBox
import com.parse.ParseFile
import proxyjob.proxijob.model.Localisation
import java.util.*
import com.parse.ParseGeoPoint
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.MainActivity
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.ConditionGeneral
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Created by alexandre on 04/02/2018.
 */

class SubscribeEntreprise : Activity() {
    var entreprise : EditText?= null
    var nb_serie : EditText?= null
    var email : EditText?= null
    var password : EditText?= null
    var phone : EditText?= null
    var address : EditText?= null
    var subscribe : Button?=null
    var geocoder: Geocoder?= null
    var addresses: List<Address>? = null
    var secteur : EditText?= null
    var addPhoto:Button?= null
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
        setContentView(R.layout.activity_subscribe_entreprise)
        entreprise = findViewById(R.id.entreprise)
        nb_serie = findViewById(R.id.nb_siret)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        phone = findViewById(R.id.phone)
        subscribe = findViewById(R.id.subscribe)
        address = findViewById(R.id.address)
        geocoder = Geocoder(this, Locale.getDefault())
        secteur = findViewById(R.id.secteur)
        addPhoto = findViewById(R.id.addPhoto)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit()
        addPhoto!!.setOnClickListener {
            showFileChooser()
        }
        findViewById<CheckBox>(R.id.condition).setOnClickListener {
            startActivity(Intent(this, ConditionGeneral::class.java))
        }
        subscribe!!.setOnClickListener {
            if (entreprise!!.text.toString() != "" && nb_serie!!.text.toString() != "" &&
                    email!!.text.toString() != "" && password!!.text.toString() != "" &&
                    phone!!.text.toString() != "" && address!!.text.toString() != "" && subscribe!!.text.toString() != "" &&
                    photoIsAdd == true &&  findViewById<CheckBox>(R.id.condition).isChecked) {
                val user = KUser()
                val company = Company()
                val location = Localisation()
                user.username = email!!.getText().toString()
                user.setPassword(password!!.getText().toString())
                user.business = true
                user.email = user.username
                user.phoneNumber = phone!!.text.toString()
                company.siret = nb_serie!!.text.toString()
                company.name = entreprise!!.text.toString()
                company.secteur = secteur!!.text.toString()
                location.address = address!!.text.toString()
                addresses = geocoder!!.getFromLocationName(address!!.text.toString().toLowerCase(), 1);
                for (address in addresses!!) {
                    var outputAddress = ""
                    for (i in 0 until address.getMaxAddressLineIndex()) {
                        outputAddress += " --- " + address.getAddressLine(i)
                    }
                }
                if ( addresses != null) {
                    val address = addresses!!.get(0)
                    val addressFragments = ArrayList<String>()

                    for (i in 0 until address.maxAddressLineIndex) {
                        addressFragments.add(address.getAddressLine(i))
                    }
                    Log.i("DEBUG ADDRESS", "" + address)
                    location.localisation = ParseGeoPoint(address.latitude, address.longitude)
                }
                location.saveInBackground {
                    company.localisation = location
                    company.logo = conversionBitmapParseFile(bitmap!!)
                    company.saveInBackground {
                        user.put("company", company)
                        user.signUpInBackground(object : SignUpCallback {
                            override fun done(e: ParseException?) {
                                if (e == null) {
                                    editor!!.putString("choice", "0")
                                    editor!!.apply()
                                        startActivity(Intent(this@SubscribeEntreprise, MainActivity::class.java))
                                } else {
                                    alert(e.message.toString()) {

                                    }.show()
                                }
                            }
                        })
                    }
                }

            } else {
                alert("Merci de remplir toutes les informations demandées") {}.show()
            }
        }
    }
    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    fun conversionBitmapParseFile(imageBitmap: Bitmap): ParseFile {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageByte = byteArrayOutputStream.toByteArray()
        return ParseFile("image_file.png", imageByte)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
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
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }


    //This method will be called when the user will tap on allow or deny
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

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
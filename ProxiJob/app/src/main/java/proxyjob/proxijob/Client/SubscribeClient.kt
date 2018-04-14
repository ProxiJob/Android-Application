package proxyjob.proxijob.Client

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.parse.ParseException
import com.parse.SignUpCallback
import org.jetbrains.anko.alert
import proxyjob.proxijob.model.KUser
import java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.parse.ParseFile
import com.parse.ParseUser
import org.jetbrains.anko.find
import proxyjob.proxijob.Login.FakePoleEmploi
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.ConditionGeneral
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat


/**
 * Created by alexandre on 04/02/2018.
 */
class SubscribeClient : Activity() {
    var firstname : EditText?= null
    var lastname : EditText?= null
    var email : EditText?= null
    var password : EditText?= null
    var birthday : EditText?= null
    var homme : CheckBox?= null
    var femme : CheckBox?= null
    var subscribe : Button?=null
    var condition: CheckBox?= null
    var myCalendar = Calendar.getInstance()
    var addPhoto:Button?= null
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    var photoIsAdd = false
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    var settings: SharedPreferences?= null
    var editor: SharedPreferences.Editor ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_demandeur)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit()
        subscribe = findViewById(R.id.subscribe)
        birthday = findViewById(R.id.layoutDate)
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        homme = findViewById(R.id.homme)
        femme = findViewById(R.id.femme)
        condition = findViewById(R.id.condition)
        addPhoto = findViewById(R.id.addPhoto)
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }
        birthday!!.setOnClickListener({
                DatePickerDialog(this@SubscribeClient, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        })
        condition!!.setOnClickListener {
            startActivity(Intent(this, ConditionGeneral::class.java))
        }
        addPhoto!!.setOnClickListener {
            showFileChooser()
        }
        subscribe!!.setOnClickListener {
            if (firstname!!.text.toString() != "" && lastname!!.text.toString() != ""
                    && email!!.text.toString() != "" && password!!.text.toString() != ""
                    && birthday!!.text.toString() != "" && checkCheckBox() && photoIsAdd == true) {
                findViewById<com.wang.avi.AVLoadingIndicatorView>(R.id.avi).show()
                val user = KUser()
                user.username = email!!.getText().toString()
                user.setPassword(password!!.getText().toString())
                user.business = false
                user.email = user.username
                user.lastname = lastname!!.text.toString()
                user.firstname = firstname!!.text.toString()
                user.birthday = Date(birthday!!.text.toString())
                user.sex =  if (homme!!.isChecked) "Homme" else "Femme"
                user.signUpInBackground(object : SignUpCallback {
                    override fun done(e: ParseException?) {
                        if (e == null) {
                            println(ParseUser.getCurrentUser().objectId)
                            KUser.getCurrentUser().profilPicture = conversionBitmapParseFile(bitmap!!)
                            KUser.getCurrentUser().save()
                            editor!!.putString("choice", "0")
                            editor!!.apply()
                                startActivity(Intent(this@SubscribeClient, FakePoleEmploi::class.java))

                        } else {
                            alert( e.message.toString()) {

                            }.show()
                        }
                    }
                })
            } else {
                alert("Merci de remplir toutes les informations demandées") {}.show()
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.FRENCH)

        birthday!!.setText(sdf.format(myCalendar.time))
    }

    private fun checkCheckBox() : Boolean{
        if (((homme!!.isChecked && femme!!.isChecked) || (!homme!!.isChecked && !femme!!.isChecked)) && !condition!!.isChecked) {
            return false
        }
        return true
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
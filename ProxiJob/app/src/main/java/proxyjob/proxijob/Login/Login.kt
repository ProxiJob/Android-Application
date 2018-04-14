package proxyjob.proxijob.Login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.parse.LogInCallback
import com.parse.ParseUser
import org.jetbrains.anko.alert
import proxyjob.proxijob.Client.SubscribeClient
import proxyjob.proxijob.Company.SubscribeEntreprise
import proxyjob.proxijob.MainActivity
import proxyjob.proxijob.R
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat
import android.provider.MediaStore
import android.R.attr.bitmap
import android.net.Uri
import android.support.v4.app.ActivityCompat
import proxyjob.proxijob.Manifest
import java.io.IOException
import android.graphics.Bitmap




/**
 * Created by alexandre on 04/02/2018.
 */

class Login : Activity() {
    //ADD
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    //

    var ident : EditText?= null
    var pass : EditText?= null
    var login : Button?= null
    var subscribe : Button?= null
    var settings: SharedPreferences?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i("DEBUG", ParseUser.getCurrentUser().objectId)

        setContentView(R.layout.activity_login)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        ident = findViewById(R.id.ident)
        pass = findViewById(R.id.pass)
        login = findViewById(R.id.login_button)
        subscribe = findViewById(R.id.signup_button)
        login!!.setOnClickListener({


           /* Log.i("INFO", "ID : " + ident!!.text + " PASS : " + pass!!.text)
            ParseUser.logInInBackground(ident!!.text.toString(), pass!!.text.toString(), LogInCallback { user, e ->
                if (user != null)
                {
                        startActivity(Intent(this@Login, MainActivity::class.java))
                } else {
                    alert( e.message.toString()) {

                    }.show()
                }
            })*/
        })

        subscribe!!.setOnClickListener({
            var choice = settings!!.getString("choice", "0")
            when (choice) {
                "0" -> startActivity(Intent(this, Subscribe::class.java))
                "1" -> startActivity(Intent(this, SubscribeClient::class.java))
                "2" -> startActivity(Intent(this, SubscribeEntreprise::class.java))
           }

        })

    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                println(bitmap)
               // imageView.setImageBitmap(bitmap)

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
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }
    }
}
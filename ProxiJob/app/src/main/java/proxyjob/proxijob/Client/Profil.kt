package proxyjob.proxijob.Client

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.parse.ParseFile
import com.parse.ParseUser
import proxyjob.proxijob.Login.Login
import proxyjob.proxijob.R
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.HashMap

/**
 * Created by alexandre on 23/02/2018.
 */

class Profil : Fragment() {
    //ADD
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    var logo: ImageView?= null
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    //
    init {

    }
    fun newInstance(): Profil {
        return Profil()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managePost()
    }
    fun managePost() {
        /*println("JE CREER LE CONTRACT")
        val params = HashMap<String, String>()
        params.put("jobId", "GIK8YJoTVx-Y8C81fAZbg")
        ParseCloud.callFunctionInBackground("getPDF", params, FunctionCallback<ParseFile> { id, e ->
            if (e == null) {
                println("NO ERROR")
                println(id)
                // ratings is 4.5
            } else {
                println("ERROR")
            }
        })*/
        /*ParseCloud.callFunctionInBackground("createPDFAtBlock", params, FunctionCallback<String> { id, e ->
            if (e == null) {
                println("NO ERROR")
                println(id)
                // ratings is 4.5
            } else {
                println("ERROR")
            }
        })*/
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.activity_profil, container, false)
        view.findViewById<Button>(R.id.informations).setOnClickListener {
            startActivity(Intent(context, InformationsActivity::class.java))
        }
        view.findViewById<Button>(R.id.cv).setOnClickListener { showFileChooser() }
        return view
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
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, filePath)
                KUser.getCurrentUser().profilPicture = conversionBitmapParseFile(bitmap!!)
                KUser.getCurrentUser().saveInBackground()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    //method to get the file path from uri
    fun getPath(uri: Uri): String {
        var cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()

        cursor = context.contentResolver.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()

        return path
    }


    //Requesting permission
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
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
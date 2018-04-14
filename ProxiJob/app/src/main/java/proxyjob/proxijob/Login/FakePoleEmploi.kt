package proxyjob.proxijob.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import proxyjob.proxijob.MainActivity
import proxyjob.proxijob.R
import java.text.SimpleDateFormat

/**
 * Created by alexandre on 20/03/2018.
 */

class FakePoleEmploi : Activity()
{
    var button : Button?= null
    var button2: Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_p_e)
        button = findViewById(R.id.btn)
        button2 = findViewById(R.id.btn1)

        button!!.setOnClickListener {
            startActivity(Intent(this@FakePoleEmploi, MainActivity::class.java))
        }

        button2!!.setOnClickListener {
            startActivity(Intent(this@FakePoleEmploi, MainActivity::class.java))
        }
    }
}
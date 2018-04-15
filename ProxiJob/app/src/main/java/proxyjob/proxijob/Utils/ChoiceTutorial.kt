package proxyjob.proxijob.Utils

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import proxyjob.proxijob.R

/**
 * Created by alexandre on 12/04/2018.
 */

class ChoiceTutorial : Activity()
{
    var client: Button ?= null
    var entreprise: Button ?= null
    var settings: SharedPreferences?= null
    var editor: SharedPreferences.Editor ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_tutorial)
        client = findViewById(R.id.client)
        entreprise = findViewById(R.id.entreprise)
        settings = this.getSharedPreferences("proxyjob.proxijob", 0)
        editor = settings!!.edit();
        client!!.setOnClickListener {
            var intent = Intent(this, TutorialScreen::class.java)
            editor!!.putString("choice", "1")
            editor!!.apply()
            intent.putExtra("choice", 1)
            startActivity(intent)
            finish()
        }
        entreprise!!.setOnClickListener {
            var intent = Intent(this, TutorialScreen::class.java)
            intent.putExtra("choice", 0)
            editor!!.putString("choice", "2")
            editor!!.apply()
            startActivity(intent)
            finish()
        }
    }
}
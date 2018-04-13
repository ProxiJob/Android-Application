package proxyjob.proxijob.Utils

import android.app.Activity
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_tutorial)
        client = findViewById(R.id.client)
        entreprise = findViewById(R.id.entreprise)
        client!!.setOnClickListener {
            var intent = Intent(this, TutorialScreen::class.java)
            intent.putExtra("choice", 1)
            startActivity(intent)
        }
        entreprise!!.setOnClickListener {
            var intent = Intent(this, TutorialScreen::class.java)
            intent.putExtra("choice", 0)
            startActivity(intent)
        }
    }
}
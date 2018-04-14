package proxyjob.proxijob.Utils

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.graphics.Color.parseColor
import com.codemybrainsout.onboarder.AhoyOnboarderActivity
import proxyjob.proxijob.MainActivity
import proxyjob.proxijob.R
import android.R.id.icon1
import android.widget.Button
import com.codemybrainsout.onboarder.AhoyOnboarderCard




/**
 * Created by alexandre on 12/04/2018.
 */

class TutorialScreen : AhoyOnboarderActivity() {
    var choice = -1
    override fun onFinishButtonPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        choice = intent.getIntExtra("choice", -1)
        //setContentView(R.layout.activity_subscribe_choice)
        val ahoyOnboarderCard1 = AhoyOnboarderCard("Inscrivez-vous", "Distes nous qui vous etes.", R.drawable.user)
        ahoyOnboarderCard1.setBackgroundColor(R.color.white)
        ahoyOnboarderCard1.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard1.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard2 = AhoyOnboarderCard("Décrouvrez", "Grâce à ProxiJob, vous allez découvrir diffèrents secteurs d'activités en travaillant.", R.drawable.test)
        ahoyOnboarderCard2.setBackgroundColor(R.color.white)
        ahoyOnboarderCard2.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard2.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard3 = AhoyOnboarderCard("Près de chez vous", "Soyez informé de tous les jobs disponibles autour de vous!", R.drawable.test)
        ahoyOnboarderCard3.setBackgroundColor(R.color.white)
        ahoyOnboarderCard3.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard3.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard3.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard4 = AhoyOnboarderCard("Postulez", "Exprimez votre intêret pour un emploi.", R.drawable.travail)
        ahoyOnboarderCard4.setBackgroundColor(R.color.white)
        ahoyOnboarderCard4.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard4.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard4.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard4.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard5 = AhoyOnboarderCard("Signez votre contrat", "Signez votre contract simplement sur notre application et commencez votre job.", R.drawable.handshake)
        ahoyOnboarderCard5.setBackgroundColor(R.color.white)
        ahoyOnboarderCard5.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard5.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard5.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard5.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard6 = AhoyOnboarderCard("Formez vous", "Après la découverte d'un secteur d'activité, nous vous proposerons une formation en adéquation!", R.drawable.desk)
        ahoyOnboarderCard6.setBackgroundColor(R.color.white)
        ahoyOnboarderCard6.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard6.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard6.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard6.setDescriptionTextSize(dpToPixels(8, this))
        //ahoyOnboarderCard1.setIconLayoutParams(10, 10, 50, 50, 50, 50)

        val ahoyOnboarderCard7 = AhoyOnboarderCard("Inscrivez-vous", "Dites nous qui vous etes.", R.drawable.user)
        ahoyOnboarderCard7.setBackgroundColor(R.color.white)
        ahoyOnboarderCard7.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard7.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard7.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard7.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard8 = AhoyOnboarderCard("Proposez un emploi", "Besoin d'une personne rapidement? Postez votre annonce et informez les demandeurs d'emploi près de chez vous.", R.drawable.email)
        ahoyOnboarderCard8.setBackgroundColor(R.color.white)
        ahoyOnboarderCard8.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard8.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard8.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard8.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard9 = AhoyOnboarderCard("Choisissez un candidat", "Une fois votre annonce postée, vous n'aurez qu'à choisir parmi les candidatures.", R.drawable.group)
        ahoyOnboarderCard9.setBackgroundColor(R.color.white)
        ahoyOnboarderCard9.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard9.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard9.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard9.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard10 = AhoyOnboarderCard("Signez votre contrat", "Zero papier! Nous générons automatiquement vos contrats. Il ne vous restera plus qu'à signer.", R.drawable.handshake)
        ahoyOnboarderCard10.setBackgroundColor(R.color.white)
        ahoyOnboarderCard10.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard10.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard10.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard10.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard11 = AhoyOnboarderCard("Valorisez", "Choisissez parmi un ensemble de mots les qualités reflétant au mieux votre futur employé.", R.drawable.like)
        ahoyOnboarderCard11.setBackgroundColor(R.color.white)
        ahoyOnboarderCard11.setTitleColor(R.color.proxi_button_purple)
        ahoyOnboarderCard11.setDescriptionColor(R.color.proxi_purple_light)
        ahoyOnboarderCard11.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard11.setDescriptionTextSize(dpToPixels(8, this))

        val pagesClient = ArrayList<AhoyOnboarderCard>()
        pagesClient.add(ahoyOnboarderCard1)
        pagesClient.add(ahoyOnboarderCard2)
        pagesClient.add(ahoyOnboarderCard3)
        pagesClient.add(ahoyOnboarderCard4)
        pagesClient.add(ahoyOnboarderCard5)
        pagesClient.add(ahoyOnboarderCard6)
        val pagesEntreprise = ArrayList<AhoyOnboarderCard>()
        pagesEntreprise.add(ahoyOnboarderCard7)
        pagesEntreprise.add(ahoyOnboarderCard8)
        pagesEntreprise.add(ahoyOnboarderCard9)
        pagesEntreprise.add(ahoyOnboarderCard10)
        pagesEntreprise.add(ahoyOnboarderCard11)
        if (choice == 0) {
            setOnboardPages(pagesEntreprise)
//            setGradientBackground()
            setColorBackground(R.color.proxi_button_purple)
            setFinishButtonTitle("Commencez l'aventure")
        } else {
            setOnboardPages(pagesClient)
//            setGradientBackground()
            setColorBackground(R.color.proxi_button_purple)
            setFinishButtonTitle("Commencez l'aventure")
        }


    }
}
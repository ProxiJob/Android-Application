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
        val ahoyOnboarderCard1 = AhoyOnboarderCard("Inscrivez-vous", "Description", R.drawable.user)
        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard1.setTitleColor(R.color.white)
        ahoyOnboarderCard1.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard2 = AhoyOnboarderCard("Décrouvrez", "Grâce à ProxiJob, vous allez découvrir diffèrents secteurs d'activités et", R.drawable.test)
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard2.setTitleColor(R.color.white)
        ahoyOnboarderCard2.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard3 = AhoyOnboarderCard("Près de chez vous", "Découvrez tous les jobs disponibles près de chez vous !", R.drawable.test)
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard3.setTitleColor(R.color.white)
        ahoyOnboarderCard3.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard3.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard4 = AhoyOnboarderCard("Postulez", "Exprimez votre intêret pour un emploi", R.drawable.travail)
        ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard4.setTitleColor(R.color.white)
        ahoyOnboarderCard4.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard4.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard4.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard5 = AhoyOnboarderCard("Signer", "Signez votre contract simplement sur notre application et commencez votre job", R.drawable.handshake)
        ahoyOnboarderCard5.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard5.setTitleColor(R.color.white)
        ahoyOnboarderCard5.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard5.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard5.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard6 = AhoyOnboarderCard("Formez vous !", "Après la découverte d'un corps de métier, nous vous proposerons une formation en adéquation avec vos envies !", R.drawable.desk)
        ahoyOnboarderCard6.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard6.setTitleColor(R.color.white)
        ahoyOnboarderCard6.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard6.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard6.setDescriptionTextSize(dpToPixels(8, this))
        //ahoyOnboarderCard1.setIconLayoutParams(10, 10, 50, 50, 50, 50)

        val ahoyOnboarderCard7 = AhoyOnboarderCard("Inscrivez-vous", "Description", R.drawable.user)
        ahoyOnboarderCard7.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard7.setTitleColor(R.color.white)
        ahoyOnboarderCard7.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard7.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard7.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard8 = AhoyOnboarderCard("Proposez un emploi", "Grâce à ProxiJob, vous allez découvrir diffèrents secteurs d'activités et", R.drawable.email)
        ahoyOnboarderCard8.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard8.setTitleColor(R.color.white)
        ahoyOnboarderCard8.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard8.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard8.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard9 = AhoyOnboarderCard("Choisisez votre futur employé", "Découvrez tous les jobs disponibles près de chez vous !", R.drawable.group)
        ahoyOnboarderCard9.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard9.setTitleColor(R.color.white)
        ahoyOnboarderCard9.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard9.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard9.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard10 = AhoyOnboarderCard("Signez votre contrat", "Exprimez votre intêret pour un emploi", R.drawable.handshake)
        ahoyOnboarderCard10.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard10.setTitleColor(R.color.white)
        ahoyOnboarderCard10.setDescriptionColor(R.color.grey_200)
        ahoyOnboarderCard10.setTitleTextSize(dpToPixels(10, this))
        ahoyOnboarderCard10.setDescriptionTextSize(dpToPixels(8, this))

        val ahoyOnboarderCard11 = AhoyOnboarderCard("Valorisez", "Signez votre contract simplement sur notre application et commencez votre job", R.drawable.handshake)
        ahoyOnboarderCard11.setBackgroundColor(R.color.black_transparent)
        ahoyOnboarderCard11.setTitleColor(R.color.white)
        ahoyOnboarderCard11.setDescriptionColor(R.color.grey_200)
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
            setGradientBackground()
            setFinishButtonTitle("Commencez l'aventure")
        } else {
            setOnboardPages(pagesClient)
            setGradientBackground()
            setFinishButtonTitle("Commencez l'aventure")
        }


    }
}
package proxyjob.proxijob.Company

import android.app.Activity
import android.os.Bundle
import proxyjob.proxijob.R
import proxyjob.proxijob.model.KUser
import java.text.SimpleDateFormat

/**
 * Created by alexandre on 15/04/2018.
 */

class InformationClient(user: KUser) : Activity() {
    var _user: KUser
    init {
       _user = user
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_client_for_company)
    }
}
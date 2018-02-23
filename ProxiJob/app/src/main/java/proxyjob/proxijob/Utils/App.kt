package proxyjob.proxijob.Utils

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import proxyjob.proxijob.model.Localisation

/**
 * Created by alexandre on 04/02/2018.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(this)
        ParseObject.registerSubclass(KUser::class.java)
        ParseObject.registerSubclass(Jobs::class.java)
        ParseObject.registerSubclass(Localisation::class.java)
        ParseObject.registerSubclass(Company::class.java)
    }
}
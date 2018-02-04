package proxyjob.proxyjob

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject
import proxyjob.proxyjob.model.KUser

/**
 * Created by alexandre on 04/02/2018.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(this)
        ParseObject.registerSubclass(KUser::class.java)
    }
}
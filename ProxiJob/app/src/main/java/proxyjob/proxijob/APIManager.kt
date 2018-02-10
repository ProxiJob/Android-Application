package proxyjob.proxijob

import android.util.Log
import com.parse.ParseQuery
import proxyjob.proxijob.model.Jobs

/**
 * Created by alexandre on 04/02/2018.
 */
class APIManager {

    companion object {
        var instance: APIManager? = null

        fun getShared(): APIManager {
            if (instance == null)
                instance = APIManager()
            return instance!!
        }
    }

    /**
     * This method uses Parse to retrieve a user's profile pictures.
     */

    fun getJobs(completionHandler: (Boolean, Error?, ArrayList<Jobs>) -> Unit) {
        var job: ParseQuery<Jobs> = ParseQuery.getQuery<Jobs>("Jobs")
        job.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Jobs>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }

    fun getJob(objectID : String, completionHandler: (Boolean, Error?, ArrayList<Jobs>) -> Unit) {
        var job: ParseQuery<Jobs> = ParseQuery.getQuery<Jobs>("Jobs")
        job.whereEqualTo("objectId", objectID)
        job.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Jobs>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }
}
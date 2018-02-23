package proxyjob.proxijob.Utils

import android.util.Log
import com.parse.ParseQuery
import com.parse.ParseUser
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.KUser
import com.parse.ParseObject
import proxyjob.proxijob.model.Company


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

    fun getMissionsForUser(completionHandler: (Boolean, Error?, ArrayList<Jobs>) -> Unit) {
        val innerQuery = ParseQuery.getQuery<ParseObject>("_User")
        innerQuery.whereEqualTo("objectId", ParseUser.getCurrentUser().objectId)
        val query = ParseQuery.getQuery<Jobs>("Jobs")
        query.whereMatchesQuery("clients", innerQuery)
        query.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Jobs>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }

    fun getCompany(completionHandler: (Boolean, Error?, ArrayList<Company>) -> Unit) {
        var company: ParseQuery<Company> = ParseQuery.getQuery<Company>("Company")
        var user = ParseUser.getCurrentUser() as KUser
        var id = user.company?.fetchIfNeeded<Company>()?.objectId
        company.whereEqualTo("objectId", id)
        company.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Company>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }

    fun getMissionsForCompany(company : ArrayList<Company>, completionHandler: (Boolean, Error?, ArrayList<Jobs>) -> Unit) {
        val innerQuery = ParseQuery.getQuery<Jobs>("Jobs")
        innerQuery.whereEqualTo("company", company[0])
        innerQuery.findInBackground { objects, e ->
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
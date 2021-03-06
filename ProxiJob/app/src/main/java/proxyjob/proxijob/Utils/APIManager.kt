package proxyjob.proxijob.Utils

import android.util.Log
import com.parse.ParseException
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
            //Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Jobs>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }

    fun getMissionsForUser(completionHandler: (Boolean, Error?, ArrayList<Jobs>) -> Unit) {
        val query = ParseQuery.getQuery<Jobs>("Jobs")
        val array = ArrayList<String>()
        array.add(KUser.getCurrentUser().objectId)
        query.whereContainedIn("postule", array)
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

    fun getCompany(obj: String, completionHandler: (Boolean, Error?, ArrayList<Company>) -> Unit) {
        var company: ParseQuery<Company> = ParseQuery.getQuery<Company>("Company")
        company.whereEqualTo("objectId", obj)
        company.include("logo")
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
        job.include("company")
        job.include("company.name")
        job.include("company.logo")
        job.include("clients")
        job.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<Jobs>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }

    fun getUsersPost(users : ArrayList<String>, completionHandler: (Boolean, Error?, ArrayList<KUser>) -> Unit) {
        val innerQuery = ParseQuery.getQuery<KUser>("_User")
        innerQuery.whereContainedIn("objectId", users)
        innerQuery.findInBackground { objects, e ->
            Log.i("DEBUG API", "" + objects.size)
            var jobs = ArrayList<KUser>(objects)
            e?.let {
                completionHandler(false, null, jobs) //e
            }
            completionHandler(true, null, jobs)
        }
    }
    fun manageJob(job: Jobs, completionHandler: (Boolean, Error?) -> Unit) {
        job.saveInBackground { e: ParseException? ->
            e.let {
                completionHandler(false, null) //e
            }
            completionHandler(true, null)
        }
    }
}
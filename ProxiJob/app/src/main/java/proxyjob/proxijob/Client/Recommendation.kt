package proxyjob.proxijob.Client

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import proxyjob.proxijob.R

/**
 * Created by alexandre on 21/03/2018.
 */

class Recommendation : Activity()
{
    var listAdapter: RecommendationListAdapter?= null
    var list : ListView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations_client)
        list = findViewById(R.id.listView)
        //listAdapter = RecommendationListAdapter(this, )
        //list!!.adapter = listAdapter
    }
}
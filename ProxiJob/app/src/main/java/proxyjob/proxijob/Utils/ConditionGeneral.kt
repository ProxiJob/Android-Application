package proxyjob.proxijob.Utils

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import proxyjob.proxijob.R

/**
 * Created by alexandre on 12/04/2018.
 */

class ConditionGeneral : Activity() {
    var button: Button?= null
    var webView: WebView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition_general)
        button = findViewById(R.id.validate)
        webView = findViewById(R.id.webview)
        webView!!.loadUrl("file:///android_asset/generalCondition.html")

        button!!.setOnClickListener {
            this.finish()
        }
    }
}
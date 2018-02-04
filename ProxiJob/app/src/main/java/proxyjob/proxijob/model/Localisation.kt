package proxyjob.proxijob.model

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject

/**
 * Created by alexandre on 04/02/2018.
 */
@ParseClassName("Localisation")
class Localisation : ParseObject() {
    var localisation: ParseGeoPoint?
        get() = this.getParseGeoPoint("localisation")
        set(value) {
            this.put("localisation", value)
        }
}
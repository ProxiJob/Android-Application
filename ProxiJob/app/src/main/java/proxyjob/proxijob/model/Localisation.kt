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
        get() = this.getParseGeoPoint("point")
        set(value) {
            this.put("point", value)
        }
    var address: String?
        get() = this.getString("address")
        set(value) {
            this.put("address", value)
        }
}
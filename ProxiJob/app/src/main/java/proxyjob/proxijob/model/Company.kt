package proxyjob.proxijob.model

import com.parse.ParseClassName
import com.parse.ParseObject

/**
 * Created by alexandre on 10/02/2018.
 */

@ParseClassName("Company")
class   Company : ParseObject() {
    var name: String?
        get() = this.getString("name")
        set(value) {
            this.put("name", value)
        }
    var siret: String?
        get() = this.getString("siret")
        set(value) {
            this.put("name", value)
        }
    var localisation: Localisation?
        get() = this.get("localisation") as Localisation
        set(value) {
            this.put("localisation", ParseObject.createWithoutData("Localisation", (value!!.objectId)))
        }
    var secteur: String?
        get() = this.getString("secteur")
        set(value) {
            this.put("secteur", value)
        }
}
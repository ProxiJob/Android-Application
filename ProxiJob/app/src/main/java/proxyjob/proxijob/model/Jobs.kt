package proxyjob.proxijob.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

/**
 * Created by alexandre on 04/02/2018.
 */
@ParseClassName("Jobs")
class Jobs : ParseObject() {
    var job: String?
        get() = this.getString("job")
        set(value) {
            this.put("job", value)
        }
    var description: String?
        get() = this.getString("description")
        set(value) {
            this.put("description", value)
        }
    var price: String?
        get() = this.getString("price")
        set(value) {
            this.put("price", value)
        }
    var contract: ParseFile?
        get() = this.getParseFile("contract")
        set(value) {
            this.put("contract", value)
        }
    var dateStart: Date?
        get() = this.getDate("dateStart")
        set(value) {
            this.put("dateStart", value)
        }
    var dateEnd: Date?
        get() = this.getDate("dateEnd")
        set(value) {
            this.put("dateEnd", value)
        }
    var client: KUser?
        get() = this.get("Client") as KUser
        set(value) {
            this.put("Client", ParseObject.createWithoutData("_User", (value!!.objectId)))
        }
    var company: KUser?
        get() = this.get("Company") as KUser
        set(value) {
            this.put("Company", ParseObject.createWithoutData("_User", (value!!.objectId)))
        }
    var localisation: Localisation?
        get() = this.get("Localisation") as Localisation
        set(value) {
            this.put("Localisation", ParseObject.createWithoutData("Localisation", (value!!.objectId)))
        }
}
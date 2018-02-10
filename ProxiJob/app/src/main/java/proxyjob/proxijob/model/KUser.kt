package proxyjob.proxijob.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

/**
 * Created by alexandre on 04/02/2018.
 */

@ParseClassName("_User")
class KUser : ParseUser() {
    var business: Boolean?
        get() = this.getBoolean("business")
        set(value) {
            this.put("business", value)
        }
    var firstname: String?
        get() = this.getString("firstName")
        set(value) {
            this.put("firstName", value)
        }
    var lastname: String?
        get() = this.getString("lastName")
        set(value) {
            this.put("lastName", value)
        }
    var phoneNumber: String?
        get() = this.getString("phoneNumber")
        set(value) {
            this.put("phoneNumber", value)
        }
    var birthday: Date?
        get() = this.getDate("birthday")
        set(value) {
            this.put("birthday", value)
        }
    var sex: String?
        get() = this.getString("sex")
        set(value) {
            this.put("sex", value)
        }
    var company: Company?
        get() = this.get("company") as Company
        set(value) {
            this.put("company", ParseObject.createWithoutData("Company", (value!!.objectId)))
        }
}
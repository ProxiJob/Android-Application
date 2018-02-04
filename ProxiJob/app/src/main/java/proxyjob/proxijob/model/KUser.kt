package proxyjob.proxijob.model

import com.parse.ParseClassName
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
    var businessName: String?
        get() = this.getString("businessName")
        set(value) {
            this.put("businessName", value)
        }
    var firstname: String?
        get() = this.getString("firstname")
        set(value) {
            this.put("firstname", value)
        }
    var lastname: String?
        get() = this.getString("lastname")
        set(value) {
            this.put("lastname", value)
        }
    var phoneNumber: String?
        get() = this.getString("phoneNumber")
        set(value) {
            this.put("phoneNumber", value)
        }
    var siret: String?
        get() = this.getString("siret")
        set(value) {
            this.put("siret", value)
        }
    var birthday: Date?
        get() = this.getDate("birthday")
        set(value) {
            this.put("birthday", value)
        }
    var sexe: String?
        get() = this.getString("sexe")
        set(value) {
            this.put("sexe", value)
        }

}
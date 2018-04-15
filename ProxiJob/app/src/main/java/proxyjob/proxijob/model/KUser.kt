package proxyjob.proxijob.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

/**
 * Created by alexandre on 04/02/2018.
 */

@ParseClassName("_User")
class KUser() : ParseUser() {
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
    var profilPicture: ParseFile?
        get() = this.get("profilPicture") as ParseFile
        set(value) {
            this.put("profilPicture", value)
        }
    var secu: String?
        get() = this.getString("secu")
        set(value) {
            this.put("secu", value)
        }
    var address: String?
        get() = this.getString("address")
        set(value) {
            this.put("address", value)
        }

    constructor(parcel: Parcel) : this() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KUser> {
        override fun createFromParcel(parcel: Parcel): KUser {
            return KUser(parcel)
        }

        override fun newArray(size: Int): Array<KUser?> {
            return arrayOfNulls(size)
        }
        fun logOut() : Unit {
            return ParseUser.logOut()
        }

        fun getCurrentUser() : KUser {
            return ParseUser.getCurrentUser() as KUser
        }
    }

}
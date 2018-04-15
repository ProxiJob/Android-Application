package proxyjob.proxijob.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

/**
 * Created by alexandre on 15/04/2018.
 */

@ParseClassName("Contracts")
class Contracts : ParseObject() {
    var contract: String?
        get() = this.getString("contract")
        set(value) {
            this.put("contract", value)
        }
    var name: String?
        get() = this.getString("name")
        set(value) {
            this.put("name", value)
        }
    var pdfFile: ParseFile?
        get() = this.getParseFile("pdfFile")
        set(value) {
            this.put("pdfFile", value)
        }
}
package proxyjob.proxijob.Utils

import android.app.Application
import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import proxyjob.proxijob.model.*


/**
 * Created by alexandre on 04/02/2018.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/avenirltstd_medium.otf")
        Parse.initialize(this)
        ParseObject.registerSubclass(KUser::class.java)
        ParseObject.registerSubclass(Jobs::class.java)
        ParseObject.registerSubclass(Localisation::class.java)
        ParseObject.registerSubclass(Company::class.java)
        ParseObject.registerSubclass(Contracts::class.java)
    }
}

object TypefaceUtil {

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context                    to work with assets
     * @param defaultFontNameToOverride  for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    fun overrideFont(context: Context, defaultFontNameToOverride: String, customFontFileNameInAssets: String) {

        val customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val newMap = HashMap<String, Typeface>()
            newMap.put("serif", customFontTypeface)
            try {
                val staticField = Typeface::class.java
                        .getDeclaredField("sSystemFontMap")
                staticField.isAccessible = true
                staticField.set(null, newMap)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        } else {
            try {
                val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
                defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField.set(null, customFontTypeface)
            } catch (e: Exception) {
                Log.e(TypefaceUtil::class.java.simpleName, "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride")
            }

        }
    }
}
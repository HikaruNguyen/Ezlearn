package com.vn.ezlearn.activity

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.modelresult.CategoryResult







/**
 * Created by manhi on 4/1/2017.
 */

class MyApplication : Application() {
    var categoryResult: CategoryResult? = null

    fun getEzlearnService(): EzlearnService = EzlearnService.Factory.create(this)

    companion object {
        fun with(context: Context): MyApplication = context.applicationContext as MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        changeAppFont()
    }

    private fun changeAppFont() {
        setDefaultFont(this, "DEFAULT", "normal.ttf")
        setDefaultFont(this, "DEFAULT_BOLD", "bold.ttf")
        setDefaultFont(this, "DEFAULT_ITALIC", "italic.ttf")
//
//        setDefaultFont(this, "MONOSPACE", "fonts/FuturaLT-Oblique.ttf");
//        setDefaultFont(this, "SANS_SERIF", "fonts/FuturaLT.ttf");
        setDefaultFont(this, "SERIF", "normal.ttf")
    }

    private fun setDefaultFont(context: Context,
                               staticTypefaceFieldName: String, fontAssetName: String) {
        val regular = Typeface.createFromAsset(context.assets,
                fontAssetName)
        replaceFont(staticTypefaceFieldName, regular)
    }

    private fun replaceFont(staticTypefaceFieldName: String,
                            newTypeface: Typeface) {
        if (isVersionGreaterOrEqualToLollipop()) {
            val newMap = HashMap<String, Typeface>()
            newMap.put("sans-serif", newTypeface)
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
                val staticField = Typeface::class.java
                        .getDeclaredField(staticTypefaceFieldName)
                staticField.isAccessible = true
                staticField.set(null, newTypeface)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    private fun isVersionGreaterOrEqualToLollipop(): Boolean =
            android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

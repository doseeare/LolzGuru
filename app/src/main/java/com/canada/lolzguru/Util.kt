package com.canada.lolzguru

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import java.util.*

object Util {
    fun chekOverLoad(webView: WebView, url: String) : Boolean {
        if (url.isEmpty() || url.startsWith("https://") || url.startsWith("http://")) return false
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            webView.context.startActivity(intent)
        } catch (e: Exception) {
            return true
        }
        return true
    }

    fun shareUrl (context : Context){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ссылка с сайта lolz guru")
        intent.putExtra(Intent.EXTRA_TEXT, AppPreferences.lastLoadedPageURL)
        context.startActivity(Intent.createChooser(intent, "Ссылка с сайта lolz guru"))
    }

   private val backStack = LinkedList<String>()
   private val forwardStack = LinkedList<String>()

    fun addBackStack(url : String) {
        backStack.add(url)
    }

    fun addForwardStack(url : String){
        forwardStack.add(url)
    }
}
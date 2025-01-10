package com.nervagodz.myapplication

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity

class residentTipsAndResources:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resident_tips_and_resources)
        val webView=findViewById<WebView>(R.id.website)
        webView.loadUrl("https://annie-lien.github.io/CityOrdinance/")
    }

}
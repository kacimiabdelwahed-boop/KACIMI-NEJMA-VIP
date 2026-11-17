package com.kacimi.nejmavip

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val purple = Color.rgb(99, 48, 139)
    private val darkPurple = Color.rgb(75, 32, 111)
    private lateinit var body: LinearLayout
    private lateinit var tabs: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = darkPurple
        window.navigationBarColor = darkPurple
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(14, 18, 14, 12)
            setBackgroundColor(purple)
        }
        page.addView(label("KACIMI NEJMA VIP", 25f, Color.WHITE, true), LinearLayout.LayoutParams(-1, 66))
        val bar = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        tabs = listOf("مباريات", "قنوات", "إعدادات").map { label(it, 17f, Color.WHITE, true) }
        tabs.forEach { t ->
            t.gravity = Gravity.CENTER
            t.setOnClickListener {
                when (tabs.indexOf(t)) {
                    0 -> showInfo("قسم المباريات")
                    1 -> showChannels()
                    else -> showInfo("إعدادات KACIMI NEJMA VIP")
                }
            }
            bar.addView(t, LinearLayout.LayoutParams(0, 52, 1f))
        }
        page.addView(bar)
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        page.addView(body, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(page)
        showChannels()
    }

    private fun label(s: String, size: Float, color: Int, bold: Boolean = false) =
        TextView(this).apply {
            text = s
            textSize = size
            setTextColor(color)
            gravity = Gravity.CENTER
            if (bold) typeface = Typeface.DEFAULT_BOLD
        }

    private fun rounded(color: Int) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = 18f
    }

    private fun showChannels() {
        body.removeAllViews()
        val scroll = ScrollView(this)
        val list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        for (r in 0..2) {
            val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
            for (c in 1..2) {
                val n = r * 2 + c
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER
                    background = rounded(Color.WHITE)
                    setPadding(6, 8, 6, 8)
                    setOnClickListener {
                        Toast.makeText(this@MainActivity, "أضف رابط بث مرخّص للقناة $n", Toast.LENGTH_LONG).show()
                    }
                }
                card.addView(label("beIN", 27f, darkPurple))
                card.addView(label("SPORTS  $n", 19f, darkPurple, true))
                val name = label("beIN $n HD", 14f, Color.WHITE, true).apply {
                    setPadding(12, 7, 12, 7)
                    background = rounded(Color.DKGRAY)
                }
                card.addView(name)
                row.addView(card, LinearLayout.LayoutParams(0, 148, 1f).apply { setMargins(5, 6, 5, 6) })
            }
            list.addView(row)
        }
        scroll.addView(list)
        body.addView(scroll)
    }

    private fun showInfo(title: String) {
        body.removeAllViews()
        body.addView(label(title, 22f, Color.WHITE, true), LinearLayout.LayoutParams(-1, 100))
        body.addView(label("واجهة أولية — يمكن إضافة المحتوى لاحقاً", 16f, Color.WHITE))
    }
}

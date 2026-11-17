package com.kacimi.nejmavip

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var urlInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 28, 22, 20)
            setBackgroundColor(0xFF160F20.toInt())
        }

        val title = TextView(this).apply {
            text = "KACIMI\nNEJMA VIP"
            textSize = 27f
            gravity = Gravity.CENTER
            setTextColor(0xFFFFFFFF.toInt())
            setPadding(8, 8, 8, 22)
        }
        root.addView(title, LinearLayout.LayoutParams(-1, -2))

        val subtitle = TextView(this).apply {
            text = "LIVE TV  •  SPORTS  •  MATCHES"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(0xFFD8C6EA.toInt())
            setPadding(0, 0, 0, 18)
        }
        root.addView(subtitle)

        urlInput = EditText(this).apply {
            hint = "ضع رابط بث تملك حق استعماله"
            setHintTextColor(0xFFB9A9C7.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setSingleLine(true)
            setPadding(14, 8, 14, 8)
            setBackgroundColor(0xFF30213D.toInt())
        }
        root.addView(urlInput, LinearLayout.LayoutParams(-1, -2))

        val playButton = Button(this).apply {
            text = "تشغيل البث"
            setOnClickListener { playStream(urlInput.text.toString().trim()) }
        }
        root.addView(playButton, LinearLayout.LayoutParams(-1, -2))

        playerView = PlayerView(this).apply {
            useController = true
        }
        val playerParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ).apply { topMargin = 18 }
        root.addView(playerView, playerParams)

        val note = TextView(this).apply {
            text = "ملاحظة: روابط Facebook قد تتطلب صلاحيات أو رموز وصول، ولا يضمن هذا المشغل عملها أو احتسابها ضمن عرض *6."
            textSize = 12f
            setTextColor(0xFFD8C6EA.toInt())
            setPadding(4, 12, 4, 0)
        }
        root.addView(note)

        setContentView(root)
    }

    private fun playStream(url: String) {
        if (!url.startsWith("https://")) {
            Toast.makeText(this, "أدخل رابط HTTPS صالحاً", Toast.LENGTH_LONG).show()
            return
        }
        try {
            player?.release()
            player = ExoPlayer.Builder(this).build().also { exo ->
                playerView.player = exo
                exo.setMediaItem(MediaItem.fromUri(url))
                exo.prepare()
                exo.playWhenReady = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "تعذر تشغيل الرابط. قد لا يكون رابط بث مباشر صالحاً.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}

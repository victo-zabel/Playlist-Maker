package ru.victoriazabelina.playlistmaker

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.core.net.toUri

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        enableEdgeToEdge()

        val backButton = findViewById<View>(R.id.btn_back)

        backButton.setOnClickListener {
            finish()
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.theme_switcher)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        switchTheme.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        switchTheme.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val shareButton = findViewById<View>(R.id.btn_share)
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.link_course))
            }
            val chooser = Intent.createChooser(shareIntent, getString(R.string.share))
            startActivity(chooser)
        }

        val supportButton = findViewById<View>(R.id.btn_support)
        supportButton.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_letter))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.content_letter))
            }
            startActivity(supportIntent)
        }

        val agreementButton = findViewById<View>(R.id.btn_agreement)
        agreementButton.setOnClickListener {
            val url = getString(R.string.offer_yp_link).toUri()
            val agreementIntent = Intent(Intent.ACTION_VIEW, url)
            startActivity(agreementIntent)
        }
    }
}
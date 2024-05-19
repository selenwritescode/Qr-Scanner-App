package com.example.biletoss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePage : AppCompatActivity() {

    lateinit var vipQr : Button
    lateinit var genelQr: Button
    lateinit var misafirQr: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        vipQr = findViewById(R.id.vip_qr)
        genelQr = findViewById(R.id.genel_qr)
        misafirQr = findViewById(R.id.misafir_qr)


        vipQr.setOnClickListener {
            val intent = Intent(this@HomePage, QRScanner::class.java)
            startActivity(intent)
        }
        genelQr.setOnClickListener {
            val intent = Intent(this@HomePage, QRScanner::class.java)
            startActivity(intent)
        }
        misafirQr.setOnClickListener {
            val intent = Intent(this@HomePage, QRScanner::class.java)
            startActivity(intent)
        }

    }
}
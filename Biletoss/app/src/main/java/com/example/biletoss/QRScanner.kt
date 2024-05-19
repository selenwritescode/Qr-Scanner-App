package com.example.biletoss

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.biletoss.databinding.ActivityQrScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class QRScanner : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {

            }
        }

    private val scanLauncher = registerForActivityResult(ScanContract()){
        result : ScanIntentResult->
        run{
            if(result.contents == null){
                Toast.makeText(this, "İptal edildi", Toast.LENGTH_SHORT).show()
            }
            else{
                setResult(result.contents)
            }
    }
    }

    private fun setResult(string: String) {
        binding.textResult.text = string
    }

    private lateinit var binding: ActivityQrScannerBinding

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
    }

    private fun initViews() {
        binding.floatingActionButton.setOnClickListener {
            checkPermissionCamera(this)
        }
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) { showCamera()

    }
    else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
        Toast.makeText(context, "Kamera izni gerekli", Toast.LENGTH_SHORT).show()
    }
    else {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
}

    private fun initBinding() {
      binding = ActivityQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
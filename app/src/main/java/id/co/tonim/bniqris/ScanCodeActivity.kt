package id.co.tonim.bniqris

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import id.co.tonim.bniqris.databinding.ActivityScancodeBinding

class ScanCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScancodeBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityScancodeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), 123
            )
        } else {
            startScaning()
        }
    }

    private fun startScaning() {

        val scannerView: CodeScannerView = binding.scanner
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback { result ->

            runOnUiThread {
                val qrString = result.text
                val qrData = qrString.split(".")
                if (qrData.size == 4) {
                    val bankSumber = qrData[0]
                    val idTransaksi = qrData[1]
                    val namaMerchant = qrData[2]
                    val nominalTransaksi = qrData[3]

                    val intent = Intent(this, DetailTransferActivity::class.java)
                    intent.putExtra("bankSumber", bankSumber)
                    intent.putExtra("idTransaksi", idTransaksi)
                    intent.putExtra("namaMerchant", namaMerchant)
                    intent.putExtra("nominalTransaksi", nominalTransaksi)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Format QR code tidak valid", Toast.LENGTH_SHORT).show()
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback { error ->
            runOnUiThread {
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin kamera diberikan", Toast.LENGTH_SHORT).show()
                startScaning()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }
}

package com.masorone.stankinqrapp.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.budiyev.android.codescanner.*
import com.masorone.stankinqrapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var valueFromScanner: TextView
    private lateinit var scannerView: CodeScannerView
    private lateinit var codeScanner: CodeScanner

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(codeScanner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        valueFromScanner = findViewById(R.id.value_from_scanner)
        scannerView = findViewById(R.id.scanner)
        codeScanner = CodeScanner(this, scannerView)
        start()
    }

    private fun start() {
        if (cameraPermissionGranted()) {
            viewModel.setupCodeScanner()
            viewModel.valueFromScanner.observe(this) {
                valueFromScanner.text = it
            }
        } else {
            requestPermission()
        }
    }

    private fun cameraPermissionGranted() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_RC
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_RC && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            start()
        } else {
            Toast.makeText(this, "Необходимо разрешение доступа к камере, для работы приложения", Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    companion object {

        private const val CAMERA_RC = 100
    }
}
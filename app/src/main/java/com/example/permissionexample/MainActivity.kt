package com.example.permissionexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.permissionexample.R
import com.google.android.material.snackbar.Snackbar


const val PERMISSION_REQUEST_PHONE_CALL = 0

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callSupport = findViewById<Button>(R.id.call_support)
        callSupport.setOnClickListener {
            //makePhoneCall()
            makePhoneCallAfterPermission(it)
        }
    }

    private fun makePhoneCallAfterPermission(view: View){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) ==
            PackageManager.PERMISSION_GRANTED) {
            makePhoneCall()
        } else {
            requestCallPermission(view)
        }
    }

    private fun requestCallPermission(view: View) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            val snack = Snackbar.make(view, "We need you permission to make a call. " +
                    "When asked please give the permission", Snackbar.LENGTH_INDEFINITE)
            snack.setAction("OK", View.OnClickListener {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                    PERMISSION_REQUEST_PHONE_CALL)
            })
            snack.show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                PERMISSION_REQUEST_PHONE_CALL)
        }
    }

    private fun makePhoneCall(){
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel: 980000000000")
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_PHONE_CALL) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this, "Permission denied to make phone call",
                    Toast.LENGTH_SHORT). show()
            }
        }
    }
}
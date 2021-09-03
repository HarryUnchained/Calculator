package com.example.calculator

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.OtpView


class Passcode : AppCompatActivity() {
    private lateinit var otpPasscode: OtpView
    private lateinit var otpConfirmPasscode: OtpView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SharedPreferencesManager.getInstance(this).passCode.isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_passcode)
        }

        initializations()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun initializations() {
        otpPasscode = findViewById(R.id.otp_passcode)
        otpConfirmPasscode = findViewById(R.id.otp_confirm_passcode)

        otpPasscode.requestFocus()

        clickListeners()


    }

    private fun clickListeners() {
        otpPasscode.setOtpCompletionListener {
            Log.d("OTP CODE::", it)

            otpConfirmPasscode.requestFocus()

        }
        otpConfirmPasscode.setOtpCompletionListener {
            if (otpPasscode.text.toString() == otpConfirmPasscode.text.toString()) {
                SharedPreferencesManager.getInstance(this).passCode = it
                Toast.makeText(this, "Passcode saved", Toast.LENGTH_SHORT).show()
                Constants.firstRun = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Passcodes don't match", Toast.LENGTH_SHORT).show()
            }

        }

    }
}


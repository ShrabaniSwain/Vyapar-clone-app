package com.example.vyaperclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.vyaperclone.databinding.ActivityOtpactivityBinding
import com.example.vyaperclone.databinding.FragmentMenuItemsBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOtpSubmit.setOnClickListener {
            val intent = Intent(this@OTPActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
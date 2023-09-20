package com.example.vyaperclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vyaperclone.databinding.ActivityOtpactivityBinding
import com.example.vyaperclone.databinding.ActivitySignUpBinding

class SIgnUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
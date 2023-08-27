package com.example.vyaperclone

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.vyaperclone.databinding.ActivityLoginBinding
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, OTPActivity::class.java)
            startActivity(intent)
        }
    }

}
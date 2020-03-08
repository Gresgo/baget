package com.urtisi.baget.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.urtisi.baget.R
import com.urtisi.baget.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    /**
     * launcher activity
     * (logging in)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        binding.buttonLogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        startActivity(Intent(this, MainActivity::class.java))
    }

}
package com.example.signinsignupusingfirebase

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import com.example.signinsignupusingfirebase.databinding.ActivitySignInBinding
import com.example.signinsignupusingfirebase.databinding.ActivityWelcomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {
    private val binding : ActivitySignInBinding by lazy{
        ActivitySignInBinding.inflate(layoutInflater)
    }

    private lateinit var auth :FirebaseAuth

    override fun onStart() {
        super.onStart()

        // check if user already signed in
        val CurrentUser : FirebaseUser? = auth.currentUser
        if(CurrentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth = FirebaseAuth.getInstance()





        val signuptext = "Didn't Have An Account ?  ( Sign Up )"
        val spannableString = SpannableString(signuptext)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, 26, 0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FFAE9D")), 26, signuptext.length, 0)

        binding.signup.text = spannableString

        binding.signup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        binding.signinbtn.setOnClickListener {
            val email = binding.loginemail.text.toString()
            val pass = binding.loginpass.text.toString()

            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Sign In Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

}
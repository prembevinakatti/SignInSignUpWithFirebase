package com.example.signinsignupusingfirebase

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import com.example.signinsignupusingfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val binding :ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth :FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth = FirebaseAuth.getInstance()

        val signintext = "Already Have An Account ? ( Sign In )"
        val spannableString = SpannableString(signintext)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")),0,25,0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FFAE9D")),25,signintext.length,0)

        binding.signintext.text = spannableString

        binding.signintext.setOnClickListener {
           startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }

        binding.signupbtn.setOnClickListener {

            //get text from editText fields
            val Email = binding.registeremail.text.toString()
            val userName = binding.registername.text.toString()
            val pass = binding.registerpass.text.toString()
            val repeatPass = binding.registerrepass.text.toString()

            //check if any field is blank
            if(Email.isEmpty() || userName.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()){
                Toast.makeText(this, "Please Fill The All Details", Toast.LENGTH_SHORT).show()
            }else if(pass != repeatPass){
                Toast.makeText(this, "Repeat Password Must Be Same", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(Email,pass)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,SignInActivity::class.java))
                        }else{
                            Toast.makeText(this, "Registration Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
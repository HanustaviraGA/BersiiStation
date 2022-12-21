package com.bersii.bersiistation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var email: String? = null
    private var password: String? = null

    private val url = "https://bersii.my.id/api/token"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        password = ""
        email = password
        etEmail = findViewById(R.id.emaillogin)
        etPassword = findViewById(R.id.passwordlogin)
        val loginbutton = findViewById<TextView>(R.id.loginbtnlogin)
        loginbutton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        email = etEmail!!.text.toString().trim { it <= ' ' }
        password = etPassword!!.text.toString().trim { it <= ' ' }
        if (email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    val data = response.toString()
                    val jArray = JSONArray(data)
                    for(i in 0 until jArray.length()){
                        val jobject = jArray.getJSONObject(i)
                        val status = jobject.getString("message")
                        if(status == "Sukses"){
                            val id = jobject.getString("id")
                            val nama = jobject.getString("nama")
                            val email = jobject.getString("email")
                            val nomor_telepon = jobject.getString("nomor_telepon")
                            val alamat = jobject.getString("alamat")
                            val token = jobject.getString("token")
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            val sharedPreference =  getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                            val editor = sharedPreference.edit()
                            editor.putString("id",id)
                            editor.putString("nama",nama)
                            editor.putString("email",email)
                            editor.putString("nomor_telepon",nomor_telepon)
                            editor.putString("alamat", alamat)
                            editor.putString("token", token)
                            editor.apply()
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@MainActivity, status, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@MainActivity,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email!!
                    data["password"] = password!!
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

}
package com.sunnni.auth_prj.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.sunnni.auth_prj.R
import com.sunnni.auth_prj.api.ServiceImpl
import com.sunnni.auth_prj.data.PreferenceManager
import com.sunnni.auth_prj.data.dto.User
import com.sunnni.auth_prj.databinding.ActivitySigninBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : BaseActivity<ActivitySigninBinding>({
    ActivitySigninBinding.inflate(it)
}) {

    private val TAG: String = SigninActivity::class.java.getSimpleName()

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher = registerForActivityResult(StartActivityForResult()) {
            // do something
        }

        setListener()
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            if (inputValidation()) {
                val id = binding.edtId.text.toString()
                val pw = binding.edtPw.text.toString()

                postSignIn(id, pw)
            }
        }
        binding.btnRegister.setOnClickListener {
            resultLauncher!!.launch(Intent(applicationContext, SignupActivity::class.java))
            // TODO : 회원가입 후 돌아오기
        }
    }

    private fun inputValidation(): Boolean {
        val id = binding.edtId.text.toString()
        val pw = binding.edtPw.text.toString()
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, R.string.hint_input_id, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(pw)) {
            Toast.makeText(this, R.string.hint_input_password, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun postSignIn(id: String, password: String) {
        val call: Call<Void> = ServiceImpl.service.postSignIn(
            User(
                id,
                null,
                password,
                null
            )
        )

        call.enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            // header로 받는 경우
                            val access_token = response.headers().get("access_token")
                            val refresh_token = response.headers().get("refresh_token")

                            // body로 받는 경우 -> dto 정의 필요
                            // val access_token_body = response.body()

                            PreferenceManager.setAccessToken(this@SigninActivity, access_token!!)
                            PreferenceManager.setRefreshToken(this@SigninActivity, refresh_token!!)

                            startActivity(Intent(this@SigninActivity, MainActivity::class.java))

                            finish()
                        }
                    } else {
                        Log.e(TAG, response.code().toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )
    }
}

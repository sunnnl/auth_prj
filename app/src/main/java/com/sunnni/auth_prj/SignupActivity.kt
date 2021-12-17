package com.sunnni.auth_prj

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.sunnni.auth_prj.api.ServiceImpl
import com.sunnni.auth_prj.data.dto.Register
import com.sunnni.auth_prj.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : BaseActivity<ActivitySignupBinding>({
    ActivitySignupBinding.inflate(it)
}) {

    private val TAG: String = SignupActivity::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.btnDone.setOnClickListener {
            if (inputValidation()) {

                val id = binding.edtId.text.toString()
                val nickname = binding.edtNickname.text.toString()
                val pw = binding.edtPw.text.toString()

                postSignUp(id, nickname, pw)
            }
        }
    }

    private fun inputValidation(): Boolean {
        val id = binding.edtId.text.toString()
        val nickname = binding.edtNickname.text.toString()
        val pw = binding.edtPw.text.toString()
        val pwChk = binding.edtPwChk.text.toString()

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, R.string.hint_input_id, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this, R.string.hint_input_nickname, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(pw)) {
            Toast.makeText(this, R.string.hint_input_password, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(pwChk)) {
            Toast.makeText(this, R.string.hint_input_password, Toast.LENGTH_SHORT).show()
            return false
        } else if (pw != pwChk) {
            Toast.makeText(this, R.string.error_password_check, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun postSignUp(id : String, nickname : String, password : String){
        val call : Call<String> = ServiceImpl.service.postSignUp(
            Register(
                id,
                nickname,
                password
            )
        )

        call.enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            // TODO : 성공
                            // TODO : 로그인 페이지로 돌아가기
                            Log.d(TAG, response.body().toString())
                            finish()
                        }
                    } else {
                        Log.e(TAG, response.code().toString())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )
    }
}
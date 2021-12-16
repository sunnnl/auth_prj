package com.sunnni.auth_prj

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.sunnni.auth_prj.databinding.ActivitySignupBinding

class SignupActivity : BaseActivity<ActivitySignupBinding>({
    ActivitySignupBinding.inflate(it)
}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.btnDone.setOnClickListener {
            if (inputValidation()) {
                // TODO : 로그인 페이지로 돌아가기
                finish()
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
}
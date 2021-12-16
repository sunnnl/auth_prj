package com.sunnni.auth_prj

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.sunnni.auth_prj.databinding.ActivitySigninBinding

class SigninActivity : BaseActivity<ActivitySigninBinding>({
    ActivitySigninBinding.inflate(it)
}) {

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher = registerForActivityResult(StartActivityForResult()) {
            // do something
        }

        setListener()
    }

    private fun setListener(){
        binding.btnLogin.setOnClickListener {
            if (inputValidation()) {
                // TODO : 로그인
                finish()
            }
        }
        binding.btnRegister.setOnClickListener {
            resultLauncher!!.launch(Intent(applicationContext, SignupActivity::class.java))
            // TODO : 회원가입 후 돌아오기
        }
    }

    private fun inputValidation() : Boolean {
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
}
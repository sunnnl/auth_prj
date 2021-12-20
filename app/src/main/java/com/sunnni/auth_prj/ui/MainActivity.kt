package com.sunnni.auth_prj.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sunnni.auth_prj.api.ServiceImpl
import com.sunnni.auth_prj.data.PreferenceManager
import com.sunnni.auth_prj.data.dto.User
import com.sunnni.auth_prj.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {
    private val TAG: String = MainActivity::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        getUserInfo()
    }

    private fun setListener(){
        binding.btnLogout.setOnClickListener {
            PreferenceManager.clearAccessToken(this@MainActivity)
            PreferenceManager.clearRefreshToken(this@MainActivity)
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }
        binding.tvMenuUserManagement.setOnClickListener {
            startActivity(Intent(this, ManageUserActivity::class.java))
        }
    }

    // 관리자와 일반 사용자의 화면을 구분하기 위함
    private fun setView(nickname : String, type : Byte){
        binding.tvNickname.text = nickname
        var type_admin = 2
        if (type == type_admin.toByte()) {
            binding.tvMenuUserManagement.visibility = View.VISIBLE
        }
    }

    private fun getUserInfo() {
        val call : Call<User> = ServiceImpl.service.getUserInfo(
            "Bearer " + PreferenceManager.getAccessToken(this@MainActivity)
        )
        call.enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            var nickname : String = response.body()!!.nickname!!
                            var type : Byte = response.body()!!.type!!
                            setView(nickname, type)
                        }
                    } else {
                        Log.e(TAG, response.code().toString())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )
    }
}
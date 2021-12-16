package com.sunnni.auth_prj

import android.os.Bundle
import com.sunnni.auth_prj.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
    }

    private fun setListener(){
        binding.btnLogout.setOnClickListener {
            // TODO : 로그아웃
            // TODO : login page
            finish()
        }
    }
}
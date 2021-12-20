package com.sunnni.auth_prj.ui

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnni.auth_prj.R
import com.sunnni.auth_prj.api.ServiceImpl
import com.sunnni.auth_prj.data.dto.User
import com.sunnni.auth_prj.databinding.ActivityManageUserBinding
import com.sunnni.auth_prj.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageUserActivity : BaseActivity<ActivityManageUserBinding>({
    ActivityManageUserBinding.inflate(it)
}) {

    private val TAG: String = ManageUserActivity::class.java.getSimpleName()

    private lateinit var userAdapter: UserAdapter
    private var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllUsers()
    }

    private fun setAdapter() {
        userAdapter = UserAdapter(this, userList, SwitchListener())
        binding.rvUser.adapter = userAdapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.rvUser.addItemDecoration(dividerItemDecoration)
    }

    private fun getAllUsers() {
        val call: Call<List<User>> = ServiceImpl.service.getUsers()
        call.enqueue(
            object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val res = response.body()!!
                            userList.addAll(res)
                            setAdapter()
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )

    }

    private fun changeUserType(id : String, type : Byte) {
        val call: Call<Void> = ServiceImpl.service.postChangeUserType(
            User(
                id,
                null,
                null,
                type
            )
        )
        call.enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            Toast.makeText(
                                this@ManageUserActivity,
                                getString(R.string.txt_complete_change),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )
    }

    inner class SwitchListener : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            var user : User = p0?.getTag() as User
            if (p1) {
                var changeType = 2
                changeUserType(user.id, changeType.toByte())
            } else {
                var changeType = 1
                changeUserType(user.id, changeType.toByte())
            }
        }
    }
}
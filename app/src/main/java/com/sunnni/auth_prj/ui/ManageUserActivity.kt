package com.sunnni.auth_prj.ui

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        touchDummy()
    }

    private fun touchDummy() {
        for (i in 0..15) {
            userList.add(
                User(
                    "id",
                    "nickname",
                    "//",
                    2
                )
            )
        }
    }

    private fun setAdapter() {
        userAdapter = UserAdapter(this, userList)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.rvUser.addItemDecoration(dividerItemDecoration)
    }

    private fun getAllUsers(){
        val call : Call<ArrayList<User>> = ServiceImpl.service.getUsers()
        call.enqueue(
            object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val res = response.body()!!
                            userList.addAll(res)
                            setAdapter()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "error: $t")
                }
            }
        )

    }
}
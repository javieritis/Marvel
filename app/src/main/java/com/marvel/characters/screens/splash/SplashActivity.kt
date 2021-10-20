package com.marvel.characters.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.marvel.characters.databinding.EmptyLayoutBinding
import com.marvel.characters.library.base.BaseActivity
import com.marvel.characters.screens.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@SuppressLint("CustomSplashScreen")
@ExperimentalSerializationApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class SplashActivity : BaseActivity<EmptyLayoutBinding>() {

    override val binding by viewBinding(EmptyLayoutBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            delay(TIME_SPLASH)
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }
    }
}

const val TIME_SPLASH: Long = 2500
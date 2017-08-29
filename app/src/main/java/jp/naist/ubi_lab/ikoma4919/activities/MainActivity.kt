package jp.naist.ubi_lab.ikoma4919.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.fragments.SplashScreenFragment

class MainActivity : AppCompatActivity() {

    var llFragmentContainer: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    override fun onStart() {
        super.onStart()

        supportFragmentManager
                .beginTransaction()
                .add(R.id.ll_fragment_container, SplashScreenFragment())
                .commit()
    }

    fun initView() {
        llFragmentContainer = findViewById(R.id.ll_fragment_container) as LinearLayout
    }


}

package jp.naist.ubi_lab.ikoma4919.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.fragments.MenuDetailsFragment
import jp.naist.ubi_lab.ikoma4919.fragments.SplashScreenFragment

/**
 * メインの Activity
 * @author yuki-mat
 */
class MainActivity : AppCompatActivity() {

    var llFragmentContainer: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initFragments()

    }

    override fun onStart() {
        super.onStart()
    }

    private fun initView() {
        llFragmentContainer = findViewById(R.id.ll_fragment_container) as LinearLayout
    }


    private fun initFragments() {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.ll_splash_container, SplashScreenFragment())
                .commit()

        Handler().postDelayed({
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.ll_fragment_container, MenuDetailsFragment())
                    .commit()
        }, 200)

    }


}

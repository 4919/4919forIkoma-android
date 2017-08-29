package jp.naist.ubi_lab.ikoma4919.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.fragments.CalendarFragment
import jp.naist.ubi_lab.ikoma4919.fragments.MenuDetailsFragment
import jp.naist.ubi_lab.ikoma4919.fragments.SplashScreenFragment

/**
 * メインの Activity
 * @author yuki-mat
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    var llFragmentContainer: LinearLayout? = null

    var ivToolbarLeft: ImageView? = null
    var ivToolbarRight: ImageView? = null

    var isCalendarShown: Boolean = false
    var isSettingsShown: Boolean = false

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

        ivToolbarLeft = findViewById(R.id.iv_toolbar_left) as ImageView
        ivToolbarLeft?.setOnClickListener(this)
        ivToolbarRight = findViewById(R.id.iv_toolbar_right) as ImageView

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.iv_toolbar_left -> {
                if(isCalendarShown) {
                    showMenuDetailFragment()
                } else {
                    showCalendarFragment()
                }
            }
            R.id.iv_toolbar_right -> {

            }
        }
    }

    private fun initFragments() {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.ll_splash_container, SplashScreenFragment())
                .commit()

        Handler().postDelayed({
            showMenuDetailFragment()
        }, 200)

    }

    private fun showCalendarFragment() {

        ivToolbarLeft?.setImageDrawable(ResourcesCompat.getDrawable(resources, R.mipmap.ic_back, null))

        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.ll_fragment_container, CalendarFragment())
                .commit()

        isCalendarShown = true
        isSettingsShown = false

    }

    private fun showMenuDetailFragment() {

        ivToolbarLeft?.setImageDrawable(ResourcesCompat.getDrawable(resources, R.mipmap.ic_calendar, null))

        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.ll_fragment_container, MenuDetailsFragment())
                .commit()

        isCalendarShown = false
        isSettingsShown = false

    }

}

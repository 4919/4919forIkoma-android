package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnticipateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView

import jp.naist.ubi_lab.ikoma4919.R

/**
 * スプラッシュスクリーン の Fragment
 * @author yuki-mat
 */
class SplashScreenFragment : DialogFragment() {
    private val TAG = "SplashScreenFragment"

    private var ivSplashLogo: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_splash_screen, container, false)
        ivSplashLogo = v.findViewById(R.id.iv_splash_logo)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun setTimer() {
        Handler().postDelayed({
            zoomInAnimation()
            Handler().postDelayed({
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, R.anim.fade_out, android.R.anim.fade_in, R.anim.fade_out)
                        .remove(this)
                        .commit()
            }, 500)
        }, 500)
    }

    private fun zoomInAnimation() {
        val scaleAnimation = ScaleAnimation(1.0f, 2.5f, 1.0f, 2.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.interpolator = AnticipateInterpolator()
        scaleAnimation.repeatCount = 0
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = 600
        ivSplashLogo?.startAnimation(scaleAnimation)
    }

}
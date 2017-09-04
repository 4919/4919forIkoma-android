package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.MenuModel
import jp.naist.ubi_lab.ikoma4919.utils.FireBaseHelper
import java.util.*


/**
 * １日の詳細メニュー の Fragment
 * @author yuki-mat
 */
class MenuDetailsFragment : Fragment(), FireBaseHelper.FireBaseEventListener, View.OnClickListener {
    private val TAG = "MenuDetailsFragment"

    private var fireBaseHelper: FireBaseHelper? = null
    private var tvMenuNameStaple: TextView? = null
    private var tvMenuNameMainDish: TextView? = null
    private var tvMenuNameSideDish: TextView? = null
    private var tvMenuNameSoup: TextView? = null
    private var tvMenuNameDrink: TextView? = null
    private var tvMenuNameDessert: TextView? = null
    private var tvMenuEnergy: TextView? = null
    private var tvMenuPoint0: TextView? = null
    private var tvMenuPoint1: TextView? = null
    private var tvMenuPoint2: TextView? = null

    private var ivPicStaple: ImageView? = null
    private var ivPicMainDish: ImageView? = null
    private var ivPicSideDish: ImageView? = null
    private var ivPicSoup: ImageView? = null
    private var ivPicDrink: ImageView? = null
    private var ivPicDessert: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_menu_details, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        tvMenuNameStaple = v.findViewById(R.id.tv_menuName_staple)
        tvMenuNameStaple?.setOnClickListener(this)
        tvMenuNameMainDish = v.findViewById(R.id.tv_menuName_mainDish)
        tvMenuNameMainDish?.setOnClickListener(this)
        tvMenuNameSideDish = v.findViewById(R.id.tv_menuName_sideDish)
        tvMenuNameSideDish?.setOnClickListener(this)
        tvMenuNameSoup = v.findViewById(R.id.tv_menuName_soup)
        tvMenuNameSoup?.setOnClickListener(this)
        tvMenuNameDrink = v.findViewById(R.id.tv_menuName_drink)
        tvMenuNameDrink?.setOnClickListener(this)
        tvMenuNameDessert = v.findViewById(R.id.tv_menuName_dessert)
        tvMenuNameDessert?.setOnClickListener(this)
        tvMenuEnergy = v.findViewById(R.id.tv_menuEnergy)
        tvMenuPoint0 = v.findViewById(R.id.tv_menuPoint_0)
        tvMenuPoint1 = v.findViewById(R.id.tv_menuPoint_1)
        tvMenuPoint2 = v.findViewById(R.id.tv_menuPoint_2)

        ivPicStaple = v.findViewById(R.id.iv_pic_staple)
        ivPicStaple?.setOnClickListener(this)
        ivPicMainDish = v.findViewById(R.id.iv_pic_main_dish)
        ivPicMainDish?.setOnClickListener(this)
        ivPicSideDish = v.findViewById(R.id.iv_pic_side_dish)
        ivPicSideDish?.setOnClickListener(this)
        ivPicSoup = v.findViewById(R.id.iv_pic_soup)
        ivPicSoup?.setOnClickListener(this)
        ivPicDrink = v.findViewById(R.id.iv_pic_drink)
        ivPicDrink?.setOnClickListener(this)
        ivPicDessert = v.findViewById(R.id.iv_pic_dessert)
        ivPicDessert?.setOnClickListener(this)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseHelper?.getMenuSummary(Date())

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSummaryFetched(menu: MenuModel) {
        tvMenuNameStaple?.text = menu.stapleName
        tvMenuNameMainDish?.text = menu.mainDishName
        tvMenuNameSideDish?.text = menu.sideDishName
        tvMenuNameSoup?.text = menu.soupName
        tvMenuNameDrink?.text = menu.drinkName
        tvMenuNameDessert?.text = menu.dessertName
        tvMenuEnergy?.text = "${menu.energy} kcal"
        tvMenuPoint0?.text = menu.point0
        tvMenuPoint1?.text = menu.point1
        tvMenuPoint2?.text = menu.point2

        ivPicStaple?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.staplePic, null))
        ivPicMainDish?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.mainDishPic, null))
        ivPicSideDish?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.sideDishPic, null))
        ivPicSoup?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.soupPic, null))
        ivPicDrink?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.drinkPic, null))
        ivPicDessert?.setImageDrawable(ResourcesCompat.getDrawable(resources, menu.dessertPic, null))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_pic_staple, R.id.tv_menuName_staple -> openDetailDialog(MenuCategory.STAPLE)
            R.id.iv_pic_main_dish, R.id.tv_menuName_mainDish -> openDetailDialog(MenuCategory.MAIN_DISH)
            R.id.iv_pic_side_dish, R.id.tv_menuName_sideDish -> openDetailDialog(MenuCategory.SIDE_DISH)
            R.id.iv_pic_soup, R.id.tv_menuName_soup -> openDetailDialog(MenuCategory.SOUP)
            R.id.iv_pic_dessert, R.id.tv_menuName_dessert -> openDetailDialog(MenuCategory.DESSERT)
            R.id.iv_pic_drink, R.id.tv_menuName_drink -> openDetailDialog(MenuCategory.DRINK)
        }
    }

    enum class MenuCategory {
        STAPLE, MAIN_DISH, SIDE_DISH, SOUP, DESSERT, DRINK
    }

    private fun openDetailDialog(category: MenuCategory) {

        activity.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.ll_dialog_container, MenuDetailDialogFragment())
                .commit()

    }

}
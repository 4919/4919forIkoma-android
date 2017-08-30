package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
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
class MenuDetailsFragment : DialogFragment(), FireBaseHelper.FireBaseEventListener {
    private val TAG = "MenuDetailsFragment"

    private var fireBaseHelper: FireBaseHelper? = null
    private var tvMenuNameStaple: TextView? = null
    private var tvMenuNameDishes: TextView? = null
    private var tvMenuNameDrink: TextView? = null
    private var tvMenuNameDessert: TextView? = null
    private var tvMenuEnergy: TextView? = null
    private var tvMenuPoint0: TextView? = null
    private var tvMenuPoint1: TextView? = null
    private var tvMenuPoint2: TextView? = null

    private var ivPicDrink: ImageView? = null
    private var ivPicDessert: ImageView? = null
    private var ivPicMainDish: ImageView? = null
    private var ivPicStaple: ImageView? = null
    private var ivPicSideDish: ImageView? = null
    private var ivPicSoup: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_menu_details, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        tvMenuNameStaple = v.findViewById(R.id.tv_menuName_staple)
        tvMenuNameDishes = v.findViewById(R.id.tv_menuName_dishes)
        tvMenuNameDrink = v.findViewById(R.id.tv_menuName_drink)
        tvMenuNameDessert = v.findViewById(R.id.tv_menuName_dessert)
        tvMenuEnergy = v.findViewById(R.id.tv_menuEnergy)
        tvMenuPoint0 = v.findViewById(R.id.tv_menuPoint_0)
        tvMenuPoint1 = v.findViewById(R.id.tv_menuPoint_1)
        tvMenuPoint2 = v.findViewById(R.id.tv_menuPoint_2)

        ivPicDrink = v.findViewById(R.id.iv_pic_drink)
        ivPicDessert = v.findViewById(R.id.iv_pic_dessert)
        ivPicMainDish = v.findViewById(R.id.iv_pic_main_dish)
        ivPicStaple = v.findViewById(R.id.iv_pic_staple)
        ivPicSideDish = v.findViewById(R.id.iv_pic_side_dish)
        ivPicSoup = v.findViewById(R.id.iv_pic_soup)

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
        tvMenuNameDishes?.text = "${menu.mainDishName}\n${menu.sideDishName}\n${menu.soupName}"
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


}
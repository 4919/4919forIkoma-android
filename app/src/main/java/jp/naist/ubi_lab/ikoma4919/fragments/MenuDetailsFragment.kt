package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.Menu
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

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseHelper?.getMenuSummary(Date())

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSummaryFetched(menu: Menu) {
        tvMenuNameStaple?.text = menu.stapleName
        tvMenuNameDishes?.text = "${menu.mainDishName}\n${menu.sideDishName}\n${menu.soupName}"
        tvMenuNameDrink?.text = menu.drinkName
        tvMenuNameDessert?.text = menu.dessertName
        tvMenuEnergy?.text = "${menu.energy} kcal"
        tvMenuPoint0?.text = menu.point0
        tvMenuPoint1?.text = menu.point1
        tvMenuPoint2?.text = menu.point2
    }


}
package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.adapters.AllergenGridAdapter
import jp.naist.ubi_lab.ikoma4919.adapters.IngredientListAdapter
import jp.naist.ubi_lab.ikoma4919.models.AllergenModel.Allergen
import jp.naist.ubi_lab.ikoma4919.models.AllergenModel.AllergenCategory.*
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.*
import jp.naist.ubi_lab.ikoma4919.utils.FireBaseHelper
import java.util.*

/**
 * １日の詳細メニュー の Fragment
 * @author yuki-mat
 */
class MenuDetailDialogFragment(): DialogFragment(), FireBaseHelper.FireBaseEventListener, View.OnClickListener {
    private val TAG = "MenuDetailDialogFragment"

    private var fireBaseHelper: FireBaseHelper? = null

    private var category = MenuCategory.STAPLE
    private var date = Date()

    private var tvMenuNameSelected: TextView? = null
    private var rvAllergenList: RecyclerView? = null
    private var rvIngredientList: RecyclerView? = null
    private var allergenGridAdapter: AllergenGridAdapter? = null
    private var ingredientListAdapter: IngredientListAdapter? = null
    private var ivBtnDialogClose: ImageView? = null

    constructor(category: MenuCategory, date: Date): this() {
        this.category = category
        this.date = date
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.dialog_menu_detail, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        tvMenuNameSelected = v.findViewById(R.id.tv_menuName_selected)


        allergenGridAdapter = AllergenGridAdapter(context)
        ingredientListAdapter = IngredientListAdapter(context)

        rvAllergenList = v.findViewById(R.id.rv_allergen_list)
        rvAllergenList?.layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        rvAllergenList?.isNestedScrollingEnabled = false
        rvAllergenList?.setHasFixedSize(true)
        rvAllergenList?.adapter = allergenGridAdapter

        rvIngredientList = v.findViewById(R.id.rv_ingredient_list)
        rvIngredientList?.layoutManager = LinearLayoutManager(context)
        rvIngredientList?.isNestedScrollingEnabled = false
        rvIngredientList?.setHasFixedSize(false)
        rvIngredientList?.adapter = ingredientListAdapter

        ivBtnDialogClose = v.findViewById(R.id.iv_dialog_close)
        ivBtnDialogClose?.setOnClickListener(this)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseHelper?.getMenuDetail(category, date)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_dialog_close -> { closeDialog() }
        }
    }

    override fun onSummaryFetched(menuSummary: MenuSummary) {
    }

    override fun onDetailFetched(menuDetail: MenuDetail) {

        allergenGridAdapter?.resetList()
        ingredientListAdapter?.resetList()

        tvMenuNameSelected?.text = menuDetail.menuName

        var allergenCounter = 0
        if(menuDetail.allergenList.size > 0) {
            menuDetail.allergenList.forEach {
                if (it != UNKNOWN) {
                    allergenGridAdapter?.addItem(Allergen(it))
                    allergenCounter++
                }
            }
        } else {
            allergenGridAdapter?.addItem(Allergen(EMPTY))
            allergenCounter = 1
        }
        allergenGridAdapter?.notifyDataSetChanged()
        rvAllergenList?.layoutParams?.height = (rvAllergenList?.layoutParams?.height ?: 0) * Math.ceil(allergenCounter / 4.0).toInt()

        if(menuDetail.ingredientList.size > 0) {
            menuDetail.ingredientList.forEach {
                if (it != "") {
                    ingredientListAdapter?.addItem(it)
                }
            }
        }
        ingredientListAdapter?.notifyDataSetChanged()

    }

    fun closeDialog() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .remove(this)
                .commit()
    }

}
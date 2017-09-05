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
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.*
import jp.naist.ubi_lab.ikoma4919.utils.FireBaseHelper
import java.util.*

/**
 * １日の詳細メニュー の Fragment
 * @author yuki-mat
 */
class MenuDetailDialogFragment(): DialogFragment(), FireBaseHelper.FireBaseEventListener {
    private val TAG = "MenuDetailDialogFragment"

    private var fireBaseHelper: FireBaseHelper? = null

    private var category = MenuCategory.STAPLE
    private var date = Date()

    private var tvMenuNameSelected: TextView? = null

    constructor(category: MenuCategory, date: Date): this() {
        this.category = category
        this.date = date
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.dialog_menu_detail, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        tvMenuNameSelected = v.findViewById(R.id.tv_menuName_selected)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseHelper?.getMenuDetail(category, date)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSummaryFetched(menuSummary: MenuSummary) {
    }

    override fun onDetailFetched(menuDetail: MenuDetail) {

        tvMenuNameSelected?.text = menuDetail.stapleName

    }


}
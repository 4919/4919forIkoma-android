package jp.naist.ubi_lab.ikoma4919.utils

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.MenuModel
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * FireBase でのやり取りのためのヘルパー
 * @author yuki-mat
 */
class FireBaseHelper(val context: Context) {
    private val TAG = "FireBaseHelper"

    private var database: FirebaseDatabase? = null
    private var listener: FireBaseEventListener? = null
    private var menu: MenuSummary? = null

    interface FireBaseEventListener {
        fun onSummaryFetched(menuSummary: MenuSummary)
        fun onDetailFetched(menuDetail: MenuDetail)
    }

    fun setFireBaseEventListener(listener: FireBaseEventListener) {
        this.listener = listener
    }

    fun getMenuSummary(date: Date) {

        val simpleDateFormat = SimpleDateFormat("yyMMdd", Locale.JAPAN)
        val targetDate = simpleDateFormat.format(date)

        database = FirebaseDatabase.getInstance()
        val ref = database?.getReference(targetDate)

        menu = MenuSummary(date)
//        Log.d(TAG, menu?.date.toString())

        ref?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numChildren = dataSnapshot.childrenCount
                if(numChildren > 0) {
                    menu?.stapleName = getMenuItemName(dataSnapshot, "staple")
                    menu?.mainDishName = getMenuItemName(dataSnapshot, "main_dish")
                    menu?.sideDishName = getMenuItemName(dataSnapshot, "side_dish")
                    menu?.soupName = getMenuItemName(dataSnapshot, "soup")
                    menu?.drinkName = context.resources.getString(R.string.initString_menuName_drink)
                    menu?.dessertName = getMenuItemName(dataSnapshot, "dessert")

                    menu?.energy = dataSnapshot.child("energy")?.getValue(String::class.java)?: "-"
                    menu?.protein = dataSnapshot.child("protein")?.getValue(String::class.java)?: "-"

                    val points = dataSnapshot.child("threePoint")?.value as ArrayList<*>
                    menu?.point0 = points[0]?.toString()?: "-"
                    menu?.point1 = points[1]?.toString()?: "-"
                    menu?.point2 = points[2]?.toString()?: "-"

                    menu?.staplePic = when {
                            menu?.stapleName.equals("-") -> { R.drawable.ic_empty }
                            check(menu?.stapleName, context.resources.getString(R.string.regexString_menu_bread)) -> { R.mipmap.pic_staple_bread }
                            check(menu?.stapleName, context.resources.getString(R.string.regexString_menu_nan)) -> { R.mipmap.pic_staple_nan }
                            else -> { R.mipmap.pic_staple_rice }
                        }
                    menu?.mainDishPic = when {
                            menu?.mainDishName.equals("-") -> { R.drawable.ic_empty }
                            check(menu?.mainDishName, context.resources.getString(R.string.regexString_menu_meat)) -> { R.mipmap.pic_main_dish_niku }
                            check(menu?.mainDishName, context.resources.getString(R.string.regexString_menu_fry)) -> { R.mipmap.pic_main_dish_fry }
                            check(menu?.mainDishName, context.resources.getString(R.string.regexString_menu_korokke)) -> { R.mipmap.pic_side_dish_korokke }
                            check(menu?.mainDishName, context.resources.getString(R.string.regexString_menu_yasai)) -> { R.mipmap.pic_main_dish_yasai }
                            else -> { R.mipmap.pic_main_dish_fish }
                        }
                    menu?.sideDishPic = when {
                            menu?.sideDishName.equals("-") -> { R.drawable.ic_empty }
                            check(menu?.sideDishName, context.resources.getString(R.string.regexString_menu_korokke)) -> { R.mipmap.pic_side_dish_korokke }
                            else -> { R.mipmap.pic_side_dish_salad }
                        }
                    menu?.soupPic = when {
                            menu?.soupName.equals("-") -> { R.drawable.ic_empty }
                            check(menu?.soupName, context.resources.getString(R.string.regexString_menu_noodle)) -> { R.mipmap.pic_soup_udon }
                            check(menu?.soupName, context.resources.getString(R.string.regexString_menu_miso)) -> { R.mipmap.pic_soup_miso }
                            else -> { R.mipmap.pic_soup_clear }
                        }
                    menu?.dessertPic = when {
                            menu?.dessertName.equals("-") -> { R.drawable.ic_empty }
                            check(menu?.dessertName, context.resources.getString(R.string.regexString_menu_jelly)) -> { R.mipmap.pic_dessert_jelly }
                            else -> { R.mipmap.pic_dessert_orange }
                        }
                    menu?.drinkPic = R.mipmap.pic_drink_milk


//                    Log.d(TAG, menu?.toString())

                    listener?.onSummaryFetched(menu?: MenuSummary(date))

                } else {
                    listener?.onSummaryFetched(MenuSummary(date))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener?.onSummaryFetched(MenuSummary(date))
            }
        })

    }


    fun getMenuDetail(category: MenuCategory, date: Date) {

        val menuDetail = MenuDetail(date)
        menuDetail.stapleName = "hoge"
        menuDetail.allergenList.add(MenuModel().getAllergenIdentifier("豚肉"))

        listener?.onDetailFetched(menuDetail)
    }


    private fun check(name: String?, regexStr: String): Boolean = name?.contains(regexStr.toRegex())?: false

    private fun getMenuItemName(dataSnapshot: DataSnapshot, category: String): String? =
            dataSnapshot.child("menu_list")?.child(category)?.child("name")?.getValue(String::class.java) ?: "-"

}
package jp.naist.ubi_lab.ikoma4919.utils

import android.content.Context
import android.util.Log
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

    private var database: FirebaseDatabase? = FirebaseDatabase.getInstance()
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
        val ref = database?.getReference(targetDate)

        val menuSummary = MenuSummary(date)
//        Log.d(TAG, menu.date.toString())

        ref?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numChildren = dataSnapshot.childrenCount
                if(numChildren > 0) {
                    menuSummary.stapleName = getMenuItemName(dataSnapshot, "staple")
                    menuSummary.mainDishName = getMenuItemName(dataSnapshot, "main_dish")
                    menuSummary.sideDishName = getMenuItemName(dataSnapshot, "side_dish")
                    menuSummary.soupName = getMenuItemName(dataSnapshot, "soup")
                    menuSummary.drinkName = context.resources.getString(R.string.initString_menuName_drink)
                    menuSummary.dessertName = getMenuItemName(dataSnapshot, "dessert")

                    menuSummary.energy = dataSnapshot.child("energy")?.getValue(String::class.java)?: "-"
                    menuSummary.protein = dataSnapshot.child("protein")?.getValue(String::class.java)?: "-"

                    val points = dataSnapshot.child("threePoint")?.value as ArrayList<*>
                    menuSummary.point0 = points[0]?.toString()?: "-"
                    menuSummary.point1 = points[1]?.toString()?: "-"
                    menuSummary.point2 = points[2]?.toString()?: "-"

                    menuSummary.staplePic = when {
                            menuSummary.stapleName.equals("-") -> { R.drawable.ic_empty }
                            check(menuSummary.stapleName, context.resources.getString(R.string.regexString_menu_bread)) -> { R.mipmap.pic_staple_bread }
                            check(menuSummary.stapleName, context.resources.getString(R.string.regexString_menu_nan)) -> { R.mipmap.pic_staple_nan }
                            else -> { R.mipmap.pic_staple_rice }
                        }
                    menuSummary.mainDishPic = when {
                            menuSummary.mainDishName.equals("-") -> { R.drawable.ic_empty }
                            check(menuSummary.mainDishName, context.resources.getString(R.string.regexString_menu_meat)) -> { R.mipmap.pic_main_dish_niku }
                            check(menuSummary.mainDishName, context.resources.getString(R.string.regexString_menu_fry)) -> { R.mipmap.pic_main_dish_fry }
                            check(menuSummary.mainDishName, context.resources.getString(R.string.regexString_menu_korokke)) -> { R.mipmap.pic_side_dish_korokke }
                            check(menuSummary.mainDishName, context.resources.getString(R.string.regexString_menu_yasai)) -> { R.mipmap.pic_main_dish_yasai }
                            else -> { R.mipmap.pic_main_dish_fish }
                        }
                    menuSummary.sideDishPic = when {
                            menuSummary.sideDishName.equals("-") -> { R.drawable.ic_empty }
                            check(menuSummary.sideDishName, context.resources.getString(R.string.regexString_menu_korokke)) -> { R.mipmap.pic_side_dish_korokke }
                            else -> { R.mipmap.pic_side_dish_salad }
                        }
                    menuSummary.soupPic = when {
                            menuSummary.soupName.equals("-") -> { R.drawable.ic_empty }
                            check(menuSummary.soupName, context.resources.getString(R.string.regexString_menu_noodle)) -> { R.mipmap.pic_soup_udon }
                            check(menuSummary.soupName, context.resources.getString(R.string.regexString_menu_miso)) -> { R.mipmap.pic_soup_miso }
                            else -> { R.mipmap.pic_soup_clear }
                        }
                    menuSummary.dessertPic = when {
                            menuSummary.dessertName.equals("-") -> { R.drawable.ic_empty }
                            check(menuSummary.dessertName, context.resources.getString(R.string.regexString_menu_jelly)) -> { R.mipmap.pic_dessert_jelly }
                            else -> { R.mipmap.pic_dessert_orange }
                        }
                    menuSummary.drinkPic = R.mipmap.pic_drink_milk

                }
                listener?.onSummaryFetched(menuSummary)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener?.onSummaryFetched(MenuSummary(date))
            }
        })

    }


    fun getMenuDetail(category: MenuCategory, date: Date) {

        val categoryStr = category.toString().toLowerCase()

        val simpleDateFormat = SimpleDateFormat("yyMMdd", Locale.JAPAN)
        val targetDate = simpleDateFormat.format(date)
        val ref = database?.getReference(targetDate)

        val menuDetail = MenuDetail(date)

        Log.d(TAG, "category.toString().toLowerCase() : ${category.toString().toLowerCase()}")

        ref?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numChildren = dataSnapshot.childrenCount
                if (numChildren > 0) {
                    menuDetail.menuName = getMenuItemName(dataSnapshot, categoryStr) ?: "-"
                    getMenuItemArray(dataSnapshot, categoryStr, "allergen")?.forEach {
                        menuDetail.allergenList.add(MenuModel().getAllergenIdentifier(it.getValue(String::class.java)))
                    }
                    getMenuItemArray(dataSnapshot, categoryStr, "ingredients")?.forEach {
                        menuDetail.ingredientList.add(it.getValue(String::class.java))
                    }
                    Log.d(TAG, menuDetail.toString())
                }
                listener?.onDetailFetched(menuDetail)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener?.onSummaryFetched(MenuSummary(date))
            }
        })

    }


    private fun check(name: String?, regexStr: String): Boolean = name?.contains(regexStr.toRegex())?: false

    private fun getMenuItemName(dataSnapshot: DataSnapshot, category: String): String? =
            dataSnapshot.child("menu_list")?.child(category)?.child("name")?.getValue(String::class.java) ?: "-"

    private fun getMenuItemArray(dataSnapshot: DataSnapshot, category: String, arrayName: String): Iterable<DataSnapshot>? =
            dataSnapshot.child("menu_list")?.child(category)?.child(arrayName)?.children

}
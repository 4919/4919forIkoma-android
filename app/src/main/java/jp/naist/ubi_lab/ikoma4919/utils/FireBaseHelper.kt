package jp.naist.ubi_lab.ikoma4919.utils

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.MenuModel
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
    private var menu: MenuModel? = null

    interface FireBaseEventListener {
        fun onSummaryFetched(menu: MenuModel)
    }

    fun setFireBaseEventListener(listener: FireBaseEventListener) {
        this.listener = listener
    }

    fun getMenuSummary(date: Date) {

        val simpleDateFormat = SimpleDateFormat("yyMMdd", Locale.JAPAN)
        val targetDate = simpleDateFormat.format(date)

        database = FirebaseDatabase.getInstance()
        val ref = database?.getReference(targetDate)

        menu = MenuModel(date)
        Log.d(TAG, menu?.date.toString())

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
                            check(menu?.stapleName, "ぱん|パン") -> { R.mipmap.pic_staple_bread }
                            check(menu?.stapleName, "ナン") -> { R.mipmap.pic_staple_nan }
                            else -> { R.mipmap.pic_staple_rice }
                        }
                    menu?.mainDishPic = when {
                            check(menu?.mainDishName, "にく|肉|チキン|ハンバーグ") -> { R.mipmap.pic_main_dish_niku }
                            check(menu?.mainDishName, "フライ|てんぷら") -> { R.mipmap.pic_main_dish_fliedfish }
                            check(menu?.mainDishName, "コロッケ") -> { R.mipmap.pic_side_dish_korokke }
                            check(menu?.mainDishName, "いため") -> { R.mipmap.pic_main_dish_yasai }
                            else -> { R.mipmap.pic_main_dish_bakedfish }
                        }
                    menu?.sideDishPic = when {
                            check(menu?.sideDishName, "コロッケ") -> { R.mipmap.pic_side_dish_korokke }
                            else -> { R.mipmap.pic_side_dish_salad }
                        }
                    menu?.soupPic = when {
                            check(menu?.soupName, "うどん|メン|めん") -> { R.mipmap.pic_soup_udon }
                            check(menu?.soupName, "みそ") -> { R.mipmap.pic_soup_miso }
                            else -> { R.mipmap.pic_soup_clear }
                        }
                    menu?.dessertPic = when {
                            check(menu?.dessertName, "ゼリー|ヨーグルト") -> { R.mipmap.pic_dessert_jelly }
                            else -> { R.mipmap.pic_dessert_orange }
                        }
                    menu?.drinkPic = R.mipmap.pic_drink_milk


                    Log.d(TAG, menu?.toString())

                    listener?.onSummaryFetched(menu?: MenuModel(date))

                } else {
                    listener?.onSummaryFetched(MenuModel(date))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener?.onSummaryFetched(MenuModel(date))
            }
        })

    }

    private fun check(name: String?, regexStr: String): Boolean = name?.contains(regexStr.toRegex())?: false

    private fun getMenuItemName(dataSnapshot: DataSnapshot, category: String): String? =
            dataSnapshot.child("menu_list")?.child(category)?.child("name")?.getValue(String::class.java) ?: "-"

}
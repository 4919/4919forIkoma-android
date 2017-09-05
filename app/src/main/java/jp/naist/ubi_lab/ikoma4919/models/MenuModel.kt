package jp.naist.ubi_lab.ikoma4919.models

import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.AllergenIdentifier.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * メニューのモデル
 * @author yuki-mat
 */
class MenuModel {

    data class MenuSummary(var date: Date,
                           var stapleName: String?,
                           var staplePic: Int,
                           var mainDishName: String?,
                           var mainDishPic: Int,
                           var sideDishName: String?,
                           var sideDishPic: Int,
                           var soupName: String?,
                           var soupPic: Int,
                           var drinkName: String?,
                           var drinkPic: Int,
                           var dessertName: String?,
                           var dessertPic: Int,
                           var energy: String?,
                           var protein: String?,
                           var point0: String?,
                           var point1: String?,
                           var point2: String?) {

        constructor(date: Date) : this(
                date,
                "-", R.mipmap.ic_launcher,
                "-", R.mipmap.ic_launcher,
                "-", R.mipmap.ic_launcher,
                "-", R.mipmap.ic_launcher,
                "-", R.mipmap.ic_launcher,
                "-", R.mipmap.ic_launcher,
                "-", "-", "-", "-", "-")

    }

    data class MenuDetail(var date: Date, var menuName: String, var allergenList: ArrayList<AllergenIdentifier>, var ingredientList: ArrayList<String>) {
        constructor(date: Date) : this(date, "-", ArrayList<AllergenIdentifier>(), ArrayList<String>())
    }

    enum class MenuCategory {
        STAPLE, MAIN_DISH, SIDE_DISH, SOUP, DESSERT, DRINK
    }

    enum class AllergenIdentifier {
        UNDEFINED,
        // 7 allergens
        SHRIMP,
        CRAB,
        WHEAT,  // 小麦
        MILK,
        PEANUT,
        SOBA,
        EGG,
        // 20 allergens
        ABALONE,     // アワビ
        BANANA,      // バナナ
        PORK,        // 豚肉
        CASHEW_NUTS, // カシューナッツ
        SOY,         // 大豆
        GELATINE,    // ゼラチン
        SESAME,      // ごま
        BEEF,        // 牛肉
        SQUID,       // イカ
    }

    val allergenMap = mapOf(
            "豚肉" to PORK,
            "小麦" to WHEAT
    )

    fun getAllergenIdentifier(allergenStr: String): AllergenIdentifier = allergenMap[allergenStr] ?: UNDEFINED

}
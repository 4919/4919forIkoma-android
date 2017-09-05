package jp.naist.ubi_lab.ikoma4919.models

import android.content.Context
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.AllergenModel.AllergenCategory.*


/**
 * メニューのモデル
 * @author yuki-mat
 */
class AllergenModel {

    enum class AllergenCategory {
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

    data class Allergen(val category: AllergenCategory, var allergenName: Int, var allergenIcon: Int) {
        init {
            allergenName = AllergenModel().allergenMap[category]?.get(0) ?: R.string.constString_allergen_unknown
            allergenIcon = AllergenModel().allergenMap[category]?.get(1) ?: R.drawable.ic_empty
        }
    }

    private val allergenMap  = mapOf(
            PORK to arrayOf(R.string.constString_allergen_pork, R.mipmap.pic_allergen_pork),
            WHEAT to arrayOf(R.string.constString_allergen_wheat, R.mipmap.ic_launcher)
    )

    fun getAllergenCategory(context: Context, allergenStr: String): AllergenCategory {
        allergenMap.forEach {
            if(context.getString(it.value[0]) == allergenStr) {
                return it.key
            }
        }
        return UNDEFINED
    }

    fun getAllergenIconResId(category: AllergenCategory): Int = allergenMap[category]?.get(1) ?: -1

}
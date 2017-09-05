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
        EMPTY,       // 該当なし
        UNKNOWN,     // 未定義
        // 7 allergens
        SHRIMP,      // えび
        CRAB,        // かに
        WHEAT,       // 小麦
        MILK,        // 乳
        PEANUT,      // 落花生
        SOBA,        // そば
        EGG,         // 卵
        // 20 allergens
        ABALONE,     // あわび
        SQUID,       // いか
        IKRA,        // いくら
        SALMON,      // さけ
        MACKEREL,    // さば
        PORK,        // 豚肉
        BEEF,        // 牛肉
        CHICKEN,     // 鶏肉
        CASHEW_NUT,  // カシューナッツ
        SOY,         // 大豆
        SESAME,      // ごま
        MATSUTAKE,   // まつたけ
        YAM,         // やまいも
        ORANGE,      // オレンジ
        APPLE,       // りんご
        KIWI,        // キウイ
        BANANA,      // バナナ
        PEACH,       // もも
        WALNUT,      // くるみ
        GELATINE,    // ゼラチン

    }

    data class Allergen(private val category: AllergenCategory) {
        var labelResourceId = AllergenModel().getAllergenLabelResourceId(category)
        var iconResourceId = AllergenModel().getAllergenIconResourceId(category)
    }

    private val allergenMap  = mapOf(
            EMPTY to arrayOf(R.string.constString_allergen_empty, R.drawable.ic_empty),
            UNKNOWN to arrayOf(R.string.constString_allergen_unknown, R.drawable.ic_empty),

            SHRIMP to arrayOf(R.string.constString_allergen_shrimp, R.mipmap.pic_allergen_shrimp),
            CRAB to arrayOf(R.string.constString_allergen_crab, R.mipmap.pic_allergen_crab),
            WHEAT to arrayOf(R.string.constString_allergen_wheat, R.mipmap.pic_allergen_wheat),
            MILK to arrayOf(R.string.constString_allergen_milk, R.mipmap.pic_allergen_milk),
            PEANUT to arrayOf(R.string.constString_allergen_peanut, R.mipmap.pic_allergen_peanut),
            SOBA to arrayOf(R.string.constString_allergen_soba, R.mipmap.pic_allergen_soba),
            EGG to arrayOf(R.string.constString_allergen_egg, R.mipmap.pic_allergen_egg),

            ABALONE to arrayOf(R.string.constString_allergen_abalone, R.mipmap.pic_allergen_abalone),
            SQUID to arrayOf(R.string.constString_allergen_squid, R.mipmap.pic_allergen_squid),
            IKRA to arrayOf(R.string.constString_allergen_ikra, R.mipmap.pic_allergen_ikra),
            SALMON to arrayOf(R.string.constString_allergen_salmon, R.mipmap.pic_allergen_salmon),
            MACKEREL to arrayOf(R.string.constString_allergen_mackerel, R.mipmap.pic_allergen_mackerel),
            PORK to arrayOf(R.string.constString_allergen_pork, R.mipmap.pic_allergen_pork),
            BEEF to arrayOf(R.string.constString_allergen_beef, R.mipmap.pic_allergen_beef),
            CHICKEN to arrayOf(R.string.constString_allergen_chicken, R.mipmap.pic_allergen_chicken),
            CASHEW_NUT to arrayOf(R.string.constString_allergen_cashew_nut, R.mipmap.pic_allergen_cashew_nut),
            SOY to arrayOf(R.string.constString_allergen_soy, R.mipmap.pic_allergen_soy),
            SESAME to arrayOf(R.string.constString_allergen_sesame, R.mipmap.pic_allergen_sesame),
            MATSUTAKE to arrayOf(R.string.constString_allergen_matsutake, R.mipmap.pic_allergen_matsutake),
            YAM to arrayOf(R.string.constString_allergen_yam, R.mipmap.pic_allergen_yam),
            ORANGE to arrayOf(R.string.constString_allergen_orange, R.mipmap.pic_allergen_orange),
            APPLE to arrayOf(R.string.constString_allergen_apple, R.mipmap.pic_allergen_apple),
            KIWI to arrayOf(R.string.constString_allergen_kiwi, R.mipmap.pic_allergen_kiwi),
            BANANA to arrayOf(R.string.constString_allergen_banana, R.mipmap.pic_allergen_banana),
            PEACH to arrayOf(R.string.constString_allergen_peach, R.mipmap.pic_allergen_peach),
            WALNUT to arrayOf(R.string.constString_allergen_walnut, R.mipmap.pic_allergen_walnut),
            GELATINE to arrayOf(R.string.constString_allergen_gelatine, R.mipmap.pic_allergen_gelatine)
    )

    fun getAllergenCategory(context: Context, allergenStr: String): AllergenCategory {
        allergenMap.forEach {
            if(context.getString(it.value[0]) == allergenStr) {
                return it.key
            }
        }
        return UNKNOWN
    }

    fun getAllergenLabelResourceId(category: AllergenCategory): Int = allergenMap[category]?.get(0) ?: R.string.constString_allergen_unknown

    fun getAllergenIconResourceId(category: AllergenCategory): Int = allergenMap[category]?.get(1) ?: R.drawable.ic_empty

}
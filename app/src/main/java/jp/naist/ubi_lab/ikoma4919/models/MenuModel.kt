package jp.naist.ubi_lab.ikoma4919.models

import jp.naist.ubi_lab.ikoma4919.R
import java.util.*

/**
 * メニューのモデル
 * @author yuki-mat
 */
data class MenuModel(
        var date: Date,
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


    enum class MenuCategory {
        STAPLE, MAIN_DISH, SIDE_DISH, SOUP, DESSERT, DRINK
    }

}
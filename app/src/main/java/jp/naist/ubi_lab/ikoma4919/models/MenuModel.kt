package jp.naist.ubi_lab.ikoma4919.models

import java.util.*

/**
 * メニューのモデル
 * @author yuki-mat
 */
data class MenuModel(
        var date: Date,
        var stapleName: String?,
        var mainDishName: String?,
        var sideDishName: String?,
        var soupName: String?,
        var drinkName: String?,
        var dessertName: String?,
        var energy: String?,
        var protein: String?,
        var point0: String?,
        var point1: String?,
        var point2: String?) {

    constructor(date: Date) : this(date, "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-")

}
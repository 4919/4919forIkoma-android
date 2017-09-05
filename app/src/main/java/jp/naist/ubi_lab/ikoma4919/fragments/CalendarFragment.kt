package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.MenuDetail
import jp.naist.ubi_lab.ikoma4919.models.MenuModel.MenuSummary
import jp.naist.ubi_lab.ikoma4919.utils.FireBaseHelper
import java.text.SimpleDateFormat
import java.util.*


/**
 * カレンダー の Fragment
 * @author yuki-mat
 */
class CalendarFragment : Fragment(), CalendarView.OnDateChangeListener, FireBaseHelper.FireBaseEventListener {
    private val TAG = "CalendarFragment"

    private var fireBaseHelper: FireBaseHelper? = null
    private var calendarView: CalendarView? = null

    private var tvMenuNameStaple: TextView? = null
    private var tvMenuNameDishes: TextView? = null
    private var tvMenuNameDrink: TextView? = null
    private var tvMenuNameDessert: TextView? = null

    private var dateStrParser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_calendar, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        calendarView = v.findViewById(R.id.calendar_view)
        calendarView?.setOnDateChangeListener(this)

        tvMenuNameStaple = v.findViewById(R.id.tv_calendar_menuName_staple)
        tvMenuNameDishes = v.findViewById(R.id.tv_calendar_menuName_dishes)
        tvMenuNameDrink = v.findViewById(R.id.tv_calendar_menuName_drink)
        tvMenuNameDessert = v.findViewById(R.id.tv_calendar_menuName_dessert)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireBaseHelper?.getMenuSummary(Date())
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSelectedDayChange(view: CalendarView?, year: Int, month: Int, day: Int) {
//        Log.d(TAG, "onSelectedDayChange : $year/${month+1}/$day")
        fireBaseHelper?.getMenuSummary(dateStrParser.parse("$year-${month+1}-$day"))
    }

    override fun onSummaryFetched(menuSummary: MenuSummary) {
        tvMenuNameStaple?.text = menuSummary.stapleName
        tvMenuNameDishes?.text = "${menuSummary.mainDishName}\n${menuSummary.sideDishName}\n${menuSummary.soupName}"
        tvMenuNameDrink?.text = menuSummary.drinkName
        tvMenuNameDessert?.text = menuSummary.dessertName
    }

    override fun onDetailFetched(menuDetail: MenuDetail) {
    }

}
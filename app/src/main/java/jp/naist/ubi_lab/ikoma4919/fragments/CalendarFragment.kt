package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.Menu
import jp.naist.ubi_lab.ikoma4919.utils.FireBaseHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * カレンダー の Fragment
 * @author yuki-mat
 */
class CalendarFragment : DialogFragment(), CalendarView.OnDateChangeListener, FireBaseHelper.FireBaseEventListener {
    private val TAG = "CalendarFragment"

    private var fireBaseHelper: FireBaseHelper? = null
    private var calendarView: CalendarView? = null
    private var dateStrParser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_calendar, container, false)

        fireBaseHelper = FireBaseHelper(context)
        fireBaseHelper?.setFireBaseEventListener(this)

        calendarView = v.findViewById(R.id.calendar_view)
        calendarView?.setOnDateChangeListener(this)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSelectedDayChange(view: CalendarView?, year: Int, month: Int, day: Int) {
        Log.d(TAG, "onSelectedDayChange : $year/${month+1}/$day")
        fireBaseHelper?.getMenuSummary(dateStrParser.parse("$year-${month+1}-$day"))
    }

    override fun onSummaryFetched(menu: Menu) {
        Log.d(TAG, menu.mainDishName)
    }

}
package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import jp.naist.ubi_lab.ikoma4919.R

/**
 * カレンダー の Fragment
 * @author yuki-mat
 */
class CalendarFragment : DialogFragment(), CalendarView.OnDateChangeListener {
    private val TAG = "CalendarFragment"

    private var calendarView: CalendarView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_calendar, container, false)
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
    }

}
package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import jp.naist.ubi_lab.ikoma4919.R

/**
 * カレンダー の Fragment
 * @author yuki-mat
 */
class CalendarFragment : DialogFragment() {
    private val TAG = "CalendarFragment"

    var calendarView: CalendarView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_calendar, container, false)
        calendarView = v.findViewById(R.id.calendar_view)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
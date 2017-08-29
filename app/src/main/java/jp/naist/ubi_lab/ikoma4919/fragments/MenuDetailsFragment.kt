package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.naist.ubi_lab.ikoma4919.R


/**
 * １日の詳細メニュー の Fragment
 * @author yuki-mat
 */
class MenuDetailsFragment : DialogFragment() {
    private val TAG = "MenuDetailsFragment"

    private var database: FirebaseDatabase? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_menu_details, container, false)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val simpleDateFormat = SimpleDateFormat("yyMMdd", Locale.JAPAN)
//        val targetDate = simpleDateFormat.format(Date())

        // for debug
        val targetDate = "170706"

        database = FirebaseDatabase.getInstance()
        val ref = database?.getReference(targetDate)
        Log.d(TAG, "ref: $ref")

        ref?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numChildren = dataSnapshot.childrenCount
                Log.d(TAG, "numChildren: $numChildren")
                if(numChildren > 0) {
                    val energy = dataSnapshot.child("energy")?.getValue(String::class.java)
                    val protein = dataSnapshot.child("protein")?.getValue(String::class.java)
                    val stapleName = getMenuItemName(dataSnapshot, "staple")
                    val mainDishName = getMenuItemName(dataSnapshot, "main_dish")
                    val sideDishName = getMenuItemName(dataSnapshot, "side_dish")
                    val soupName = getMenuItemName(dataSnapshot, "soup")
                    val dessertName = getMenuItemName(dataSnapshot, "dessert")
                    Log.d(TAG, "energy: $energy, protein: $protein, staple: $stapleName, mainDish: $mainDishName, sideDish: $sideDishName, soup: $soupName, dessert: $dessertName")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getMenuItemName(dataSnapshot: DataSnapshot, category: String): String? =
            dataSnapshot.child("menu_list").child(category).child("name")?.getValue(String::class.java)

}
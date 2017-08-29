package jp.naist.ubi_lab.ikoma4919.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private var tvMenuNameStaple: TextView? = null
    private var tvMenuNameDishes: TextView? = null
    private var tvMenuNameDrink: TextView? = null
    private var tvMenuNameDessert: TextView? = null
    private var tvMenuEnergy: TextView? = null
    private var tvMenuPoint0: TextView? = null
    private var tvMenuPoint1: TextView? = null
    private var tvMenuPoint2: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_menu_details, container, false)

        tvMenuNameStaple = v.findViewById(R.id.tv_menuName_staple)
        tvMenuNameDishes = v.findViewById(R.id.tv_menuName_dishes)
        tvMenuNameDrink = v.findViewById(R.id.tv_menuName_drink)
        tvMenuNameDessert = v.findViewById(R.id.tv_menuName_dessert)
        tvMenuEnergy = v.findViewById(R.id.tv_menuEnergy)
        tvMenuPoint0 = v.findViewById(R.id.tv_menuPoint_0)
        tvMenuPoint1 = v.findViewById(R.id.tv_menuPoint_1)
        tvMenuPoint2 = v.findViewById(R.id.tv_menuPoint_2)

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
                    val drinkName = resources.getString(R.string.initString_menuName_drink)
                    val dessertName = getMenuItemName(dataSnapshot, "dessert")
                    Log.d(TAG, "energy: $energy, protein: $protein, staple: $stapleName, mainDish: $mainDishName, sideDish: $sideDishName, soup: $soupName, dessert: $dessertName")

                    tvMenuNameStaple?.text = stapleName
                    tvMenuNameDishes?.text = "$mainDishName\n$sideDishName\n$soupName"
                    tvMenuNameDrink?.text = drinkName
                    tvMenuNameDessert?.text = dessertName
                    tvMenuEnergy?.text = "$energy kcal"

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getMenuItemName(dataSnapshot: DataSnapshot, category: String): String? =
            dataSnapshot.child("menu_list")?.child(category)?.child("name")?.getValue(String::class.java)

}
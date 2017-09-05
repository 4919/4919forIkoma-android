package jp.naist.ubi_lab.ikoma4919.adapters

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.AllergenModel

/**
 * 食材 の リストアダプタ
 * @author yuki-mat
 */
class IngredientListAdapter(private val context: Context, private val ingredientList: ArrayList<String>) : RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {
    private val TAG = "IngredientListAdapter"

    constructor(context: Context): this(context, ArrayList<String>())

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvIngredientName: TextView = v.findViewById(R.id.tv_ingredient_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IngredientListAdapter.ViewHolder {
        val v = mInflater.inflate(R.layout.list_item_ingredient, viewGroup, false)
        return IngredientListAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ingredientList[position]
        holder.tvIngredientName.text = item
    }

    override fun getItemCount(): Int = ingredientList.size

    fun resetList() {
        ingredientList.clear()
    }

    fun addItem(ingredientName: String) {
        ingredientList.add(ingredientName)
    }

}
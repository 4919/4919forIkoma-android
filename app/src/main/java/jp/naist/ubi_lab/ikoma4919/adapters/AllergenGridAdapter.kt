package jp.naist.ubi_lab.ikoma4919.adapters

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import jp.naist.ubi_lab.ikoma4919.R
import jp.naist.ubi_lab.ikoma4919.models.AllergenModel.*


/**
 * アレルゲン の リストアダプタ
 * @author yuki-mat
 */
class AllergenGridAdapter(private val context: Context, private val allergenList: ArrayList<Allergen>) : RecyclerView.Adapter<AllergenGridAdapter.ViewHolder>() {
    private val TAG = "AllergenGridAdapter"

    constructor(context: Context): this(context, ArrayList<Allergen>())

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ivAllergenIcon: ImageView = v.findViewById(R.id.iv_pic_allergen)
        var tvAllergenLabel: TextView = v.findViewById(R.id.tv_label_allergen)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllergenGridAdapter.ViewHolder {
        val v = mInflater.inflate(R.layout.grid_item_allergen, viewGroup, false)
        return AllergenGridAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = allergenList[position]
        holder.ivAllergenIcon.setImageDrawable(ResourcesCompat.getDrawable(context.resources, item.iconResourceId, null))
        holder.tvAllergenLabel.text = context.getString(item.labelResourceId)
    }

    override fun getItemCount(): Int = allergenList.size

    fun resetList() {
        allergenList.clear()
    }

    fun addItem(allergen: Allergen) {
        allergenList.add(allergen)
    }

}
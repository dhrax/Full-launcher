package com.daisa.launcher

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class AppDrawerAdapter(private val context: Context, private val appList: MutableList<AppInfo>) :
    BaseAdapter() {


    override fun getCount(): Int = appList.size

    override fun getItem(pos: Int): Any = appList[pos]

    override fun getItemId(pos: Int): Long = pos.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View =
            if (convertView == null) {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.item_app_homescreen, parent, false)
            } else {
                convertView
            }

        val layout: LinearLayout = v.findViewById(R.id.appItemLayout)
        val mImage: ImageView = v.findViewById(R.id.homeAppIcon)
        val mLabel: TextView = v.findViewById(R.id.homeAppLabel)

        mImage.setImageDrawable(appList[pos].icon)
        mLabel.text = appList[pos].label


        layout.setOnClickListener {
            Log.d(TAG_DEBUG, "Pulsado en adapter")
            openAppPressed(appList[pos])
        }

        return v
    }

    private fun openAppPressed(app: AppInfo) {
        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName!!)
        context.startActivity(intent)
    }

}
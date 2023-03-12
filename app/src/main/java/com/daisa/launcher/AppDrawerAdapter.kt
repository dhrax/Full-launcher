package com.daisa.launcher

import android.content.ClipData
import android.content.ClipData.Item
import android.content.ClipDescription
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class AppDrawerAdapter(private val context: Context, private var appList: MutableList<AppInfo>) :
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

        val mImage: ImageView = v.findViewById(R.id.homeAppIcon)
        val mLabel: TextView = v.findViewById(R.id.homeAppLabel)

        mImage.setImageDrawable(appList[pos].icon)
        mLabel.text = appList[pos].label

        v.tag = appList[pos].packageName

        v.setOnClickListener {
            Log.d(TAG_DEBUG, "Pulsado en adapter")
            openAppPressed(appList[pos])
        }

        v.setOnLongClickListener {

            val item = Item(v.tag as CharSequence)

            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(v.tag.toString(), mimeTypes, item)
            val shadowBuilder = DragShadowBuilder(v)

            v.startDragAndDrop(data, shadowBuilder, null, 0)

            return@setOnLongClickListener true
        }

        return v
    }

    private fun openAppPressed(app: AppInfo) {
        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName!!)
        context.startActivity(intent)
    }

    fun updateAdapter(newAppList: MutableList<AppInfo>) {
        appList = newAppList
        notifyDataSetChanged()
    }

}
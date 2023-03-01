package com.daisa.launcher

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/**

Standard list to show what AppInfo objects are being loaded into the drawer

 */
//@Deprecated(level = DeprecationLevel.WARNING, message = "Not longer in use")
class AppListAdapter(private val activity: Activity) : RecyclerView.Adapter<AppListAdapter.ViewHolder>(){

    private val appsListDisplayed: MutableList<AppInfo>

    init {
        if (appsList.size == 0)
            getAllApps(activity.packageManager)
        else {
            getAllApps(activity.packageManager)
            notifyDataSetChanged()
        }

        appsListDisplayed = ArrayList()
        appsListDisplayed.addAll(appsList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var label: TextView = itemView.findViewById(R.id.appLabel)
        var icon: ImageView = itemView.findViewById(R.id.appIcon) as ImageView

        override fun onClick(v: View){
            val pos = adapterPosition
            val context: Context = v.context
            val appPackageName = appsListDisplayed[pos].packageName.toString()

            val intent = activity.packageManager.getLaunchIntentForPackage(appPackageName)
            context.startActivity(intent)


        }
        init { itemView.setOnClickListener(this) }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int){
        val appLabel = appsListDisplayed[i].label.toString()
        val appIcon = appsListDisplayed[i].icon

        viewHolder.label.text = appLabel
        viewHolder.icon.setImageDrawable(appIcon)
    }

    override fun getItemCount(): Int { return appsListDisplayed.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.app_display_info, parent, false)
        return ViewHolder(view)
    }
}
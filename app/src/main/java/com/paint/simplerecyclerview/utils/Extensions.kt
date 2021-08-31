package com.paint.simplerecyclerview.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import java.util.*

fun getUniqueId(): String = "${Calendar.getInstance().timeInMillis}-${UUID.randomUUID()}"

fun String.showInToast(context: Context, length: Int = Toast.LENGTH_SHORT) {
    val text = this.getStringFromResources(context.packageName, context.resources)
    Toast.makeText(context, text, length).show()
}

fun String.getStringFromResources(packageName: String, resources: Resources): String {
    val resId = resources.getIdentifier(this, "string", packageName)
    return resources.getString(resId)
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

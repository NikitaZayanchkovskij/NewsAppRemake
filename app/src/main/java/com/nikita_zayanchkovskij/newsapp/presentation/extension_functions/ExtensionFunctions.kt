package com.nikita_zayanchkovskij.newsapp.presentation.extension_functions


import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nikita_zayanchkovskij.newsapp.R
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.BookmarksFragment
import com.nikita_zayanchkovskij.newsapp.presentation.fragments.DetailedNewsInfoFragment


private val bookmarksFragment = BookmarksFragment.newInstance()
var detailedNewsInfoFragmentWasOpenedFromBookmarks: Boolean = false


fun Fragment.openDetailedNewsInfoFragment() {
    (activity as AppCompatActivity)
        .supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.fragmentHolder, DetailedNewsInfoFragment.newInstance())
        .commit()
}


fun Fragment.openBookmarksFragment() {
    (activity as AppCompatActivity)
        .supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.fragmentHolder, bookmarksFragment)
        .commit()
}


fun AppCompatActivity.openBookmarksFragment() {
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.fragmentHolder, bookmarksFragment)
        .commit()
}


fun AppCompatActivity.closeBookmarksFragment() {
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .remove(bookmarksFragment)
        .commit()
}


fun Fragment.closeDetailedNewsInfoFragment(fragment: Fragment) {
    (activity as AppCompatActivity)
        .supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .remove(fragment)
        .commit()
}


fun Fragment.showToast(context: AppCompatActivity, toastText: String) {
    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
}
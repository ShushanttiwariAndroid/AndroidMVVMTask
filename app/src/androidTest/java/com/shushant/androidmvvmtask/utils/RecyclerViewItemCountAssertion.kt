package com.shushant.androidmvvmtask.utils

import android.view.View
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.NoMatchingViewException
import androidx.recyclerview.widget.RecyclerView
import com.shushant.androidmvvmtask.utils.RecyclerViewItemCountAssertion
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

class RecyclerViewItemCountAssertion private constructor(private val matcher: Matcher<Int>) :
    ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        MatcherAssert.assertThat(adapter!!.itemCount, matcher)
    }

    companion object {
        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return withItemCount(Matchers.`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }
}
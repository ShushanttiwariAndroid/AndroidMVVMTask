package com.shushant.androidmvvmtask.ui

import androidx.activity.viewModels
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.shushant.androidmvvmtask.R
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.androidmvvmtask.repository.DemoRepository
import com.shushant.androidmvvmtask.utils.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.shushant.androidmvvmtask.utils.getValue
import com.shushant.androidmvvmtask.utils.isVisible
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.lessThan
import org.hamcrest.Matchers.lessThanOrEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    var hiltRule = HiltAndroidRule(this)

    //val activityScenario = ActivityScenarioRule(MainActivity::class.java)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var dao:DemoDao


    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)
        .around(instantTaskExecutorRule)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun checkVIewISVisible() = runBlocking {
        val currentListSize = dao.getData().size
        onView(withId(R.id.sandbox_rv)).isVisible()
        onView(withId(R.id.sandbox_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.swipeLeft()
            )
        )

        assertThat(dao.getData().size, lessThanOrEqualTo(currentListSize -1))
    }

}
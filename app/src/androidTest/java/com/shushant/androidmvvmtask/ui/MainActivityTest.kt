package com.shushant.androidmvvmtask.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.shushant.androidmvvmtask.R
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.androidmvvmtask.utils.isVisible
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
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

    private var hiltRule = HiltAndroidRule(this)

    //val activityScenario = ActivityScenarioRule(MainActivity::class.java)
   // private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var dao:DemoDao


    private var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
       // .around(activityTestRule)
        .around(instantTaskExecutorRule)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun checkVIewISVisible() = runBlocking {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val currentListSize = dao.getData().size
        onView(withId(R.id.sandbox_rv)).isVisible()
        onView(withId(R.id.sandbox_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.swipeLeft()
            )
        )

        assertThat(dao.getData().size, lessThanOrEqualTo(currentListSize -1))

        activityScenario.close()
    }

}
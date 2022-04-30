package org.techtown.layouts

import androidx.core.content.contentValuesOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$10.00"))))
    }
    @Test
    fun calculate_18_percent_tip(){
        calculateTest(50.0,18,9.0)
    }
    @Test
    fun calculate_15_percent_tip(){
        calculateTest(50.0,15,8.0)
    }


    @Test
    fun calculate_roundup_uncheck(){
        onView(withId(R.id.round_up_switch)).perform(click())
        calculateTest(50.0,15,7.5)
    }

}
fun calculateTest(cost :Double, percent:Int, amount:Double){
    onView(withId(R.id.cost_of_service_edit_text))
        .perform(typeText("$cost"))

    val percentRadioId = when(percent){
        20-> R.id.option_twenty_percent
        18-> R.id.option_eighteen_percent
        else-> R.id.option_fifteen_percent
    }

    onView(withId(percentRadioId)).perform(click())
    onView(withId(R.id.calculate_button)).perform(click())

    onView(withId(R.id.tip_result))
        .check(matches(withText(containsString("$amount"))))
}
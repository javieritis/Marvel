package com.marvel.characters.characters_list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.marvel.characters.R
import com.marvel.characters.screens.characterdetails.CharacterDetailsActivity
import com.marvel.characters.screens.home.HomeActivity
import com.marvel.characters.waitFor
import kotlinx.serialization.ExperimentalSerializationApi
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalSerializationApi
@RunWith(AndroidJUnit4ClassRunner::class)
class TestCharactersListTest {

    @get: Rule
    val homeActivityRule: IntentsTestRule<HomeActivity> = IntentsTestRule(HomeActivity::class.java)

    @get: Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)
    
    @Test
    fun checkIfLoadingViewIsVisibleAtStart() {
        onView(withId(R.id.layout_loading)).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfFabIsHiddenAtStart() {
        onView(withId(R.id.fab)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkScreenDetailsCharacter() {
        onView(isRoot()).perform(waitFor(2500))

        onView(withId(R.id.rvCharacters)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )
        onView(withId(R.id.rvCharacters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        intended(hasComponent(CharacterDetailsActivity::class.java.name))
    }
}
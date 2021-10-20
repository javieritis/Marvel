package com.marvel.characters.character_details_screen

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.marvel.characters.R
import com.marvel.characters.data.character.AvailableModel
import com.marvel.characters.data.character.Character
import com.marvel.characters.data.image.Image
import com.marvel.characters.screens.characterdetails.CharacterDetailsActivity
import kotlinx.serialization.ExperimentalSerializationApi
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalSerializationApi
@RunWith(AndroidJUnit4::class)
class CharacterDetailsScreenTest {

    @get: Rule
    val activityRule = ActivityTestRule(CharacterDetailsActivity::class.java)

    private var characterMock = Character(
        id = 1,
        name = "Spiderman",
        description = "Strip spider webs",
        thumbnail = Image("image_path", ".jpg"),
        comics = AvailableModel(available = 0),
        series = AvailableModel(available = 0)
    )

//    @Before
//    fun setupTestConfiguration() {
//        val intent = Intent()
//        intent.putExtra(CharacterDetailsActivity.EXTRA_CHARACTER, characterMock)
//        activityRule.launchActivity(intent)
//    }

//    @BindValue
//    var getDeviceInfoUseCase: GetComicListUseCase = mock(GetComicListUseCase::class.java)

    private fun launchIntent(mockCharacter: Character) {
        val intent = Intent()
        intent.putExtra(CharacterDetailsActivity.EXTRA_CHARACTER, mockCharacter)
        activityRule.launchActivity(intent)
    }

    @Test
    fun checkNameCharacterInToolbar() {
        launchIntent(mockCharacter = characterMock)
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(characterMock.name))))
    }

    @Test
    fun checkIfComicsContainerIsNotDisplayed() {
        launchIntent(mockCharacter = characterMock)
        onView(withId(R.id.container_comics)).check(matches(Matchers.not(isDisplayed())))
    }

    @Test
    fun checkIfSeriesContainerIsNotDisplayed() {
        launchIntent(mockCharacter = characterMock)
        onView(withId(R.id.container_comics)).check(matches(Matchers.not(isDisplayed())))
    }

    //https://medium.com/swlh/unit-testing-with-dagger-2-brewing-a-potion-with-fakes-mocks-and-custom-rules-in-android-7f0ab7b22cb
//    @Test
//    fun checkIfComicsContainerIsDisplayed() {
//        launchIntent(mockCharacter = characterMock.apply {
//            comics = AvailableModel(available = 1)
//        })
//        onView(withId(R.id.container_comics)).check(matches(isDisplayed()))
//    }

//    @Test
//    fun checkIfSeriesContainerIsDisplayed() {
//        launchIntent(mockCharacter = characterMock.apply {
//            series = AvailableModel(1)
//        })
//        onView(withId(R.id.container_series)).check(matches(ViewMatchers.isDisplayed()))
//    }
}
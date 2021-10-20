package com.marvel.characters.library

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.marvel.characters.data.character.Character
import com.marvel.characters.screens.characterdetails.CharacterDetailsActivity
import com.marvel.characters.screens.image.ImageDetailsActivity
import kotlinx.serialization.ExperimentalSerializationApi
import java.lang.ref.WeakReference

@ExperimentalSerializationApi
class ActivityNavigator {

    private lateinit var activityRef: WeakReference<Activity>

    fun setActivity(activity: Activity) {
        activityRef = WeakReference(activity)
    }

    fun activity(): Activity? {
        return activityRef.get()
    }

    fun openCharacterDetails(character: Character, imageTransitionName: String?, bundle: Bundle?) {
        activity()?.startActivity(
            Intent(activity(), CharacterDetailsActivity::class.java)
                .putExtra(
                    CharacterDetailsActivity.EXTRA_CHARACTER,
                    character
                )
                .putExtra(
                    CharacterDetailsActivity.EXTRA_IMAGE_TRANSITION_NAME,
                    imageTransitionName
                ), bundle
        )
    }

    fun openImageDetails(imageUrl: String) {
        activity()?.startActivity(
            Intent(activity(), ImageDetailsActivity::class.java).putExtra(
                ImageDetailsActivity.EXTRA_IMAGE_URL,
                imageUrl
            )
        )
    }
}
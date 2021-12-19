package com.kernel.android.data.repository.shared

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.core.content.edit
import com.kernel.android.data.key.SharedKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class TruckSharedPreferenceRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : TruckSharedPreferenceRepository {

    var pref: SharedPreferences = context.getSharedPreferences(SharedKeys.NAME, MODE_PRIVATE)
    var editor: Editor = pref.edit()

    override fun getPhotosUriSet(): Set<String> {
        return pref.getStringSet(SharedKeys.PHOTOS, emptySet()).orEmpty()
    }

    override fun putPhotosUriSet(set: MutableSet<String>) {
        pref.edit {
            putStringSet(SharedKeys.PHOTOS, set)
        }
    }

}
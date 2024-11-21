package com.testing.catatan.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Note(
    val title: String?,
    val noteDesc: String?,
    val date: String
) : Parcelable
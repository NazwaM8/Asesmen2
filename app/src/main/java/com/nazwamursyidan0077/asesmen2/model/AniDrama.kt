package com.nazwamursyidan0077.asesmen2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anidrama")
data class AniDrama(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val type: String,
    val episode: Int,
    val releaseDate: Int,
    val rating: Int,
    val isDeleted: Boolean = false
)

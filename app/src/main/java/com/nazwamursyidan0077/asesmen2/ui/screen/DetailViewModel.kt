package com.nazwamursyidan0077.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazwamursyidan0077.asesmen2.database.AniDramaDao
import com.nazwamursyidan0077.asesmen2.model.AniDrama
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: AniDramaDao) : ViewModel() {

    fun insert(title: String, releaseDate: Int, type: String, eps: Int, rating: Int) {
        val aniDrama = AniDrama(
            title = title,
            releaseDate = releaseDate,
            type = type,
            episode = eps,
            rating = rating
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(aniDrama)
        }
    }
    fun getAniDrama(id: Long): AniDrama? {
        return null
    }
}
package com.nazwamursyidan0077.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazwamursyidan0077.asesmen2.database.AniDramaDao
import com.nazwamursyidan0077.asesmen2.model.AniDrama
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: AniDramaDao) : ViewModel() {

    val data: StateFlow<List<AniDrama>> = dao.getAniDrama().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}

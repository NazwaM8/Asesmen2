package com.nazwamursyidan0077.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazwamursyidan0077.asesmen2.database.AniDramaDao
import com.nazwamursyidan0077.asesmen2.model.AniDrama
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrashViewModel(private val dao: AniDramaDao) : ViewModel() {

    val data: StateFlow<List<AniDrama>> = dao.getDeletedAniDrama().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun restore(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreById(id)
        }
    }
}
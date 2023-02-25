@file:OptIn(ExperimentalSerializationApi::class)

package my.notalonelyday.bsuir.android.notes.data.repo

import kotlinx.serialization.ExperimentalSerializationApi

class RepoProvider(
    private val fsNotesRepository: FSNotesRepository,
    private val dbNotesRepository: DBNotesRepository
) {
    var repoType: RepoType = RepoType.ROOM

    fun getRepo(): NotesRepository {
        return when (repoType) {
            RepoType.FS -> fsNotesRepository
            RepoType.ROOM -> dbNotesRepository
        }
    }


    enum class RepoType {
        FS, ROOM
    }
}
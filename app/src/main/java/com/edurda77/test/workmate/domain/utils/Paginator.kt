package com.edurda77.test.workmate.domain.utils

class Paginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> ResultWork<Item, DataError>,
    private val getNextKey: suspend (currentKey: Key, result: Item) -> Key,
    private val onError: suspend (DataError) -> Unit,
    private val onSuccess: suspend (result: Item, newKey: Key) -> Unit,
    private val endReached: (currentKey: Key, result: Item) -> Boolean
) {

    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isEndReached = false

    suspend fun loadNextItems() {
        if(isMakingRequest || isEndReached) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)
        isMakingRequest = false

        when (result) {
            is ResultWork.Error -> {
                onError(result.error)
                onLoadUpdated(false)
            }
            is ResultWork.Success -> {
                currentKey = getNextKey(currentKey, result.data)

                onSuccess(result.data, currentKey)

                onLoadUpdated(false)

                isEndReached = endReached(currentKey, result.data)
            }
        }
    }

    fun reset() {
        currentKey = initialKey
        isEndReached = false
    }
}
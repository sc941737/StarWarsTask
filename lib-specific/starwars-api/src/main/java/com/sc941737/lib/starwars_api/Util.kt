package com.sc941737.lib.starwars_api

import com.sc941737.lib.network.ResultWrapper

interface ResultUnwrapper {
    suspend fun <T> executeRequest(
        onSuccess: ((T) -> Unit)? = null,
        onError: ((String) -> Unit)? = null,
        block: suspend () -> ResultWrapper<T>,
    ) = when (val response = block()) {
        is ResultWrapper.Success -> onSuccess?.let { it(response.value) }
        is ResultWrapper.RedirectError -> onError?.let { it(response.message) }
        is ResultWrapper.RequestError -> onError?.let { it(response.message) }
        is ResultWrapper.ServerError -> onError?.let { it(response.message) }
        is ResultWrapper.UnknownError -> onError?.let { it(response.message) }
    }
}
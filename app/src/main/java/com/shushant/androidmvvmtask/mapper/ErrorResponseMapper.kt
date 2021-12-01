package com.shushant.androidmvvmtask.mapper

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

/**
 * A mapper for mapping [ApiResponse.Failure.Error] response as custom [DemoResponseMapper] instance.
 *
 * @see [ApiErrorModelMapper](https://github.com/skydoves/sandwich#apierrormodelmapper)
 */
object ErrorResponseMapper : ApiErrorModelMapper<DemoResponseMapper> {

    /**
     * maps the [ApiResponse.Failure.Error] to the [DemoResponseMapper] using the mapper.
     *
     * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
     * @return A customized [DemoResponseMapper] error response.
     */
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): DemoResponseMapper {
        return DemoResponseMapper(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}

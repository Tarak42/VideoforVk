package akhmedoff.usman.videoforvk.data.repository

import akhmedoff.usman.videoforvk.data.api.VkApi
import akhmedoff.usman.videoforvk.model.ResponseVideo
import akhmedoff.usman.videoforvk.model.Video
import android.arch.paging.PositionalDataSource
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import akhmedoff.usman.videoforvk.model.Response as ApiResponse

class VideosDataSource(
    private val vkApi: VkApi,
    private val ownerId: String?,
    private val videoId: String?,
    private val albumId: String?,
    private val token: String
) : PositionalDataSource<Video>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Video>) {
        vkApi.getVideos(
            ownerId = ownerId,
            videos = videoId,
            albumId = albumId,
            count = params.loadSize,
            offset = params.startPosition.toLong(),
            token = token
        ).enqueue(object : Callback<ResponseVideo> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ResponseVideo>?, t: Throwable?) {
                Log.e("callback", "ERROR: " + t.toString())
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<ResponseVideo>?,
                response: Response<ResponseVideo>?
            ) {
                response?.body()?.let {
                    callback.onResult(it.items)
                }
            }

        })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Video>) {
        val apiSource = vkApi.getVideos(
            ownerId = ownerId,
            videos = videoId,
            albumId = albumId,
            count = params.requestedLoadSize,
            offset = 0,
            token = token
        )

        try {
            val response = apiSource.execute()

            val items = response.body()?.items ?: emptyList()

            callback.onResult(items, 0)
        } catch (exception: Exception) {
            Log.e("exception", exception.toString())
        }
    }

}
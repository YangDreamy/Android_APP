package fr.epita.android.hellogame

import retrofit2.Call
import retrofit2.http.GET // Android 最热门的网络请求库
import retrofit2.http.Query

interface WSInterface {

    @GET("api/game/list")
    fun getTodolist(): Call<List<ToDoObject>>

    @GET("api/game/details")
    fun getDetailesByID(@Query("game_id")game_id: Int) : Call<GameDetaliObject>
}
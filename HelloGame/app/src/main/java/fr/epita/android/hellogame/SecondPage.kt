package fr.epita.android.hellogame

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)
        val myMessage = intent.getStringExtra("Message")
        var game_id = myMessage.toInt()
        var data: GameDetaliObject

        var callback: Callback<GameDetaliObject> = object : Callback<GameDetaliObject> {
            override fun onFailure(call: Call<GameDetaliObject>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<GameDetaliObject>,
                response: Response<GameDetaliObject>
            ) {
                Log.d("xxx", "WS ok")
                if (response.code() == 200) {//HTTP 200 OK成功状态响应代码表示请求已成功。默认情况下，200响应是可缓存的。
                    data = response.body()!!  //data is a body
                    //Toast.makeText(this@SecondPage,"Got" + data.size,Toast.LENGTH_SHORT).show()
                    var media = data.picture.toString()
                    if (media !== null) {
                        Glide.with(this@SecondPage)
                            .load(media)
                            .into(imageViewdetalis)
                    } else {
                        imageViewdetalis.setImageResource(R.drawable.ic_launcher_background)
                    }

                    namecontent.text = data.name
                    typecontent.text = data.type
                    nbcontent.text = data.player.toString()
                    yearcontent.text =data.year.toString()
                    description.text = data.description_en
                    detailbutton.setOnClickListener{
                        val url = data.url
                        val implicitIntent : Intent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(url)
                        startActivity(implicitIntent)
                    }

                }
            }
        }
        val baseURL = "https://androidlessonsapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service = retrofit.create(WSInterface::class.java)
        service.getDetailesByID(game_id).enqueue(callback)

    }
}

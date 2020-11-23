package fr.epita.android.hellogame


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data : List<ToDoObject>
        var callback : Callback<List<ToDoObject>> = object : Callback<List<ToDoObject>>{
            override fun onFailure(call: Call<List<ToDoObject>>, t: Throwable) {
                TODO("Not yet implemented")
            }
            //get random numbers the range is [min,max)
            fun cofoxRandom(min: Int = 0, max: Int): Int {
                var t_min = min
                var t_max = max
                if (t_min > t_max) {
                    var temp: Int
                    temp = t_min
                    t_min = t_max
                    t_max = temp
                }
                var random = (Math.floor(Math.random() * (t_max - t_min))).toInt()
                return random
            }
            override fun onResponse(call: Call<List<ToDoObject>>, response: Response<List<ToDoObject>>) {
                Log.d("xxx","WS ok")
                if (response.code() == 200){//HTTP 200 OK成功状态响应代码表示请求已成功。默认情况下，200响应是可缓存的。
                    data = response.body()!!  //data is a body
                    //Toast.makeText(this@MainActivity,"Got" + data.size,Toast.LENGTH_SHORT).show() //toast 是一种临时的可以取消的弹出窗口
                    var ranarray = intArrayOf(0,0,0,0)
                    val sameSet = mutableSetOf<Int>()
                    while(true){
                        sameSet.add(cofoxRandom(1,data.size+1))
                        if(sameSet.size == 4)
                            break
                        //ranarray[i-1] = cofoxRandom(1,data.size+1)
                    }
                    var i = 0
                    for(value in sameSet){
                        ranarray[i++] = value.toInt()
                    }
                    var media1 = data.get(ranarray[0]).picture.toString()
                    var id1 = data.get(ranarray[0]).id.toString()
                    if (media1 !== null) {
                        Glide.with(this@MainActivity)
                            .load(media1)
                            .into(imageview)
                    } else {
                        imageview.setImageResource(R.drawable.ic_launcher_background)
                    }
                    imageview.setOnClickListener{
                        val explicitIntent : Intent = Intent(this@MainActivity, SecondPage::class.java)
                        explicitIntent.putExtra("Message",id1) // Message = Goodbye
                        startActivity(explicitIntent)

                    }
                    var media2 = data.get(ranarray[1]).picture.toString()
                    var id2 = data.get(ranarray[1]).id.toString()
                    if (media2 !== null) {
                        Glide.with(this@MainActivity)
                            .load(media2)
                            .into(imageview2)
                    } else {
                        imageview.setImageResource(R.drawable.ic_launcher_background)
                    }
                    imageview2.setOnClickListener{
                        val explicitIntent : Intent = Intent(this@MainActivity, SecondPage::class.java)
                        explicitIntent.putExtra("Message",id2) // Message = Goodbye
                        startActivity(explicitIntent)

                    }
                    var media3 = data.get(ranarray[2]).picture.toString()
                    var id3 = data.get(ranarray[2]).id.toString()
                    if (media3 !== null) {
                        Glide.with(this@MainActivity)
                            .load(media3)
                            .into(imageview3)
                    } else {
                        imageview.setImageResource(R.drawable.ic_launcher_background)
                    }
                    imageview3.setOnClickListener{
                        val explicitIntent : Intent = Intent(this@MainActivity, SecondPage::class.java)
                        explicitIntent.putExtra("Message",id3) // Message = Goodbye
                        startActivity(explicitIntent)

                    }
                    var media4 = data.get(ranarray[3]).picture.toString()
                    var id4 = data.get(ranarray[3]).id.toString()
                    if (media4 !== null) {
                        Glide.with(this@MainActivity)
                            .load(media4)
                            .into(imageview4)
                    } else {
                        imageview.setImageResource(R.drawable.ic_launcher_background)
                    }
                    imageview4.setOnClickListener{
                        val explicitIntent : Intent = Intent(this@MainActivity, SecondPage::class.java)
                        explicitIntent.putExtra("Message",id4) // Message = Goodbye
                        startActivity(explicitIntent)
                    }
                }
            }
        }
        val baseURL = "https://androidlessonsapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service = retrofit.create(WSInterface::class.java)
        service.getTodolist().enqueue(callback)


    }
}

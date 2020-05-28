package com.example.bookshelf

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject




class MainActivity : AppCompatActivity() {
    private var bookAPI: String ="&key=AIzaSyDBGlCR5u1xt0ghhveDtJ-OWiZur68cQBk"
    private val baseUrl: String ="https://www.googleapis.com/books/v1/volumes?q="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener{
            progressBar.visibility= View.VISIBLE
            if(etSearch?.text.toString()=="") {
                etSearch?.error = "Enter book name"
                progressBar.visibility= View.GONE
                return@setOnClickListener
            }

            val list=ArrayList<Book>()
            val queue:RequestQueue = Volley.newRequestQueue(this)
            val myURL: String = baseUrl+etSearch.text.replace("\\s".toRegex(), "+")+"&maxResults=40"+bookAPI
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, myURL, null,
                Response.Listener { response ->
                    progressBar.visibility= View.GONE
                    var items:JSONArray=response.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        var author: String ="No Data"
                        var image: String=""
                        val title: String = items.getJSONObject(i).getJSONObject("volumeInfo").getString("title")
                        val volumeInfo: JSONObject = items.getJSONObject(i).getJSONObject("volumeInfo")
                        val authorArray = volumeInfo.optJSONArray("authors")
                        if (authorArray != null )
                            author = authorArray.getString(0)
                        val thumbnailUrlObject = volumeInfo.optJSONObject("imageLinks")
                        if (thumbnailUrlObject != null && thumbnailUrlObject.has("thumbnail")) {
                            image = thumbnailUrlObject.getString("thumbnail")
                        }
                        list.add(Book(image,title,author))
                    }
                    rvBooks.apply {
                        layoutManager=LinearLayoutManager(this@MainActivity)
                        adapter=BookAdapter(list)
                        setHasFixedSize(true)
                    }
                },
                Response.ErrorListener { error ->
                    Log.i("trial",error.message.toString()+" "+error.cause.toString())
                }
            )
            queue.add(jsonObjectRequest)
        }
    }

}

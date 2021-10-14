package com.example.rssfeed

import android.os.AsyncTask
import android.os.AsyncTask.execute
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    var topSongs = mutableListOf<Songs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        FetchTopSongs.execute()

    }

    private inner class FetchTopSongs: AsyncTask<Void, Void, MutableList<Song>(){
        val parser = XMLParser()
        override fun doInBackground(vararg p0: Void?): MutableList<Song> {
            val url = URL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=100/xml")
            val urlConnection = url.openConnection() as HttpURLConnection

            topSongs = urlConnection.inputStream?.let {
                parser.parse(it)
            } as MutableList<Song>
            return topSongs
        }

        override fun onPostExecute(result: MutableList<Song>?) {
            super.onPostExecute(result)
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topSongs)
            listView.adapter = adapter
        }
    }
}
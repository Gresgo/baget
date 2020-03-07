package com.urtisi.baget.util

import android.os.AsyncTask
import android.util.Xml
import com.urtisi.baget.ui.feed.site.FeedViewModel
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class RSSParser(private val caller: FeedViewModel) : AsyncTask<Void, Void, ArrayList<RSSModel>>() {
    /**
     * async task for loading and parsing RSS from website
     */
    private val URllink = "http://uisi.ru/uisi/general/news.php?rss=y"

    /**
     * loading rss
     */
    override fun doInBackground(vararg params: Void?): ArrayList<RSSModel> {
        var feed = ArrayList<RSSModel>()
        try{
            val url = URL(URllink)
            val inputStream = url.openConnection().getInputStream()
            feed = parseRSS(inputStream)
        } catch (e: Error){

        }

        return feed
    }

    /**
     * parse (clear html tags)
     */
    private fun parseRSS(inp: InputStream) : ArrayList<RSSModel> {

        var isItem = false
        var title: String ?= null
        var link: String ?= null
        var description: String ?= null
        var pubDate: String ?= null
        val rss = ArrayList<RSSModel>()

        try{
            val parser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inp, null)

            parser.nextTag()

            while (parser.next() != XmlPullParser.END_DOCUMENT){

                val eventType = parser.eventType
                val name = parser.name
                name ?: continue

                if (eventType == XmlPullParser.END_TAG){
                    if (name.equals("item", true)){
                        isItem = false
                    }
                    continue
                }

                if (eventType == XmlPullParser.START_TAG){
                    if (name.equals("item", true)){
                        isItem = true
                        continue
                    }
                }

                var result = ""
                if (parser.next() == XmlPullParser.TEXT){
                    result = parser.text
                    parser.nextTag()
                }

                when (name.toLowerCase(Locale.ENGLISH)){
                    "title" -> title = result
                    "link" -> link = result
                    "description" -> {
                        description = Regex("""<.*?>""").replace(result, "")
                        description = Regex("""\s*(?=\n)""").replace(description,"")//(?<=\s)\s
                        description = Regex("""\t""").replace(description," - ")
//                        Log.d("mems", description)
                    }
                    "pubdate" -> pubDate = result
                }

                if (title != null && link != null && description != null && pubDate != null){
                    if (isItem){
                        rss.add(RSSModel(title, description.trim(), pubDate, link))
                    }

                    title = null
                    link = null
                    description = null
                    pubDate = null
                    isItem = false
                }

            }

        } catch (e: Error){

        } finally {
            inp.close()
        }

        return rss
    }

    override fun onPostExecute(result: ArrayList<RSSModel>?) {
            caller.onDataReady(result!!)
    }
}
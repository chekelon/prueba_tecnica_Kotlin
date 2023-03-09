package com.example.parsearxml2.data.api

import android.util.Log
import com.example.parsearxml2.model.Job
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL

class JobApiClient {
    // We don't use namespaces
     private val ns: String = ""


    fun get():String {
        //GlobalScope.launch(Dispatchers.IO) {
            var response=""
            val url = URL("https://people-pro.com/xml-feed/indeed")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty("Accept", "application/xml,text/xml,application/xhtml+xml") // The format of response we want to get from the server
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false
            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {

                 response = httpURLConnection.inputStream.bufferedReader(Charsets.UTF_8)
                    .use {
                        it.readText()
                    }
                
                /*val XmlConvert = ParsearXMLtoJson()
                //val jobs = XmlConvert.parse(httpURLConnection.inputStream)
                val jobs= XmlConvert.leer(response)

                    jobs.forEach{
                        Log.i("Title",it.title.toString())
                    }*/

            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
           return response
        //}

    }

    fun getNewJobs(response:String):List<Job> {
        val factory = XmlPullParserFactory.newInstance()
        factory.setNamespaceAware(true);
        val xpp = factory.newPullParser();
        val jobs = mutableListOf<Job>()


        xpp.setInput(  StringReader ( response ) );
        var eventType = xpp.getEventType();

        var title: String? = null
        var date: String? = null
        var referencenumber: String? = null
        var url :String? = null
        var company:String? = null
        var city:String? = null
        var country:String? = null
        var description:String? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(eventType == XmlPullParser.START_TAG && xpp.getName()=="job" ) {
                //System.out.println("Start tag "+xpp.getName())
                while (xpp.next() != XmlPullParser.END_TAG) {
                    if (xpp.eventType != XmlPullParser.START_TAG) {
                        continue
                    }
                    when (xpp.name) {
                        "title" -> title = readTitle(xpp)
                        "date" -> date = readDate(xpp)
                        "referencenumber" -> referencenumber = readReferencesNumber(xpp)
                        "url" -> url = readUrl(xpp)
                        "company" -> company = readCompany(xpp)
                        "city" -> city = readCity(xpp)
                        "country" -> country = readCountry(xpp)
                        "description" -> description = readDescription(xpp)
                        else -> skip(xpp)
                    }
                }

                jobs.add(Job(title=title,date=date,referencenumber=referencenumber,url=url,company=company,city=city,country=country, description = description))
                //System.out.println("End tag "+xpp.getName())
            } else if(eventType == XmlPullParser.END_TAG && xpp.getName() == "job") {
                //System.out.println("End tag "+xpp.getName())

            } else if(eventType == XmlPullParser.TEXT && xpp.getName() != "indeed-apply-data") {
                //System.out.println(xpp.getText())
            }
            eventType = xpp.next();
        }
        System.out.println("End document");

        return jobs
    }




    // Processes title tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    // For the tags title and summary, extracts their text values.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.next()
        }
        return result
    }
    // Processes date tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDate(parser: XmlPullParser):String{
        parser.require(XmlPullParser.START_TAG, ns, "date")
        val date = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "date")
        return date
    }


    // Processes referencenumber tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readReferencesNumber(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "referencenumber")
        val summary = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "referencenumber")
        return summary
    }

    // Processes url tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readUrl(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "url")
        val url = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "url")
        return url
    }

    // Processes company tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readCompany(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "company")
        val company = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "company")
        return company
    }

    // Processes city tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readCity(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "city")
        val city = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "city")
        return city
    }

    // Processes country tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readCountry(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "country")
        val country = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "country")
        return country
    }

    // Processes descripction tags in the source.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "description")
        return description
    }



    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
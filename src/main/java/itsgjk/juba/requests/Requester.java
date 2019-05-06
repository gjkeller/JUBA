package itsgjk.juba.requests;

import okhttp3.OkHttpClient;

public class Requester {

    OkHttpClient client;

    public Requester(){
        client = new OkHttpClient();
    }

    //https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html
    //https://www.foreach.be/blog/parallel-and-asynchronous-programming-in-java-8
}

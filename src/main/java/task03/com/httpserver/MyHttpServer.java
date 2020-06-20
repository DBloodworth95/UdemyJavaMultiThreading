package task03.com.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/*
    * A simple program which demonstrates a book word-counter hosted on a http server.
    * The server accepts requests for a given word and simply returns the number of times
    * that word is found in the book.
    * Use the NUMBER_OF_THREADS const to set the amount of threads to handle http requests.
 */
public class MyHttpServer {
    private static final String INPUT_FILE = "resource/task03/httpserver/war_and_peace.txt";

    private static final int NUMBER_OF_THREADS = 1;

    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        //Create the HttpSever.
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //Create a context & pass a handler to deal with the request.
        server.createContext("/search", new WordCountHandler(text));
        //Create the thread pool.
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        //Sets the servers thread pool & start
        server.setExecutor(executor);
        server.start();
    }
    //Handles the word count.
    private static class WordCountHandler implements HttpHandler {
        private String text;

        public WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            //Gets the query request.
            String query = httpExchange.getRequestURI().getQuery();
            //Split the key value via "=" char.
            String[] keyValue = query.split("=");
            //Store the action value.
            String action = keyValue[0];
            //Store the word to be counted.
            String word = keyValue[1];
            //If the action word is not "word", send bad response.
            if (!action.equals("word")) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }

            long count = countWord(word);
            //Store the word count into a bytearray.
            byte[] response = Long.toString(count).getBytes();
            //Send a success response & the amount of bytes.
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            //Writes the word count to the response body of the page.
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = text.indexOf(word, index);

                if (index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }
    }
}

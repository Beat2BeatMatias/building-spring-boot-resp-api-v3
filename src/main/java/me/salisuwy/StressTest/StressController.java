package me.salisuwy.StressTest;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
public class StressController {

    @PostMapping(value = "/stress/loan/{loanId}/thread/{threads}/delay/{delay}", produces = "application/json; charset=utf-8")
    public String stress(@PathVariable("loanId") Long loanId, @PathVariable("delay") int delay, @PathVariable("threads") int threads, @RequestBody GrantRefundCreationCommand grantRefundCreationCommand, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String url=String.format("http://api.internal.ml.com/credits-alpha/loans/%s/grant_refunds",loanId);
        String json=new Gson().toJson(grantRefundCreationCommand);
        CompletableFuture<String> partialResponse = null;

        System.out.println(threads);
        System.out.println(delay);
        System.out.println(url);

        for (int i = 0; i < threads; i++) {
            partialResponse = CompletableFuture.supplyAsync(()->{
                return readUrl(url,json);
            });
            Thread.sleep(delay);
        }

       return partialResponse != null ? partialResponse.get() : null;
    }
    private static String readUrl(String urlString, String json){

        BufferedReader reader=null;
        byte[] out = json.getBytes();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("x-caller-id", "289889080");
            connection.setRequestProperty("x-idempotency-key", "1");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
            try(OutputStream os = connection.getOutputStream()){
                os.write(json.getBytes());
            }

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            int read = 0;
            StringBuffer buffer = new StringBuffer();
            char[] chars = new char[1024];
            while((read = reader.read(chars)) != -1){
                buffer.append(chars,0,read);
            }
            return buffer.toString();
        } catch (Exception e){
            return e.getMessage();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

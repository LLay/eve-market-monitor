package app.callback;

import okhttp3.Response;

import java.io.IOException;

public class PrintResponseCallback implements Callback {
  public void call(Response response) {
    try {
      System.out.println(response.body().string());
    } catch (IOException e) {
      System.err.println("Error reading response");
      e.printStackTrace();
    }

  }
}

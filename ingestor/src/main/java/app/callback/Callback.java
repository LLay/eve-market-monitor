package app.callback;

import okhttp3.Response;

public interface Callback {
    void call(Response response);
}

package com.example.giphyapp;

import java.util.List;

public class GiphyResponse {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        private Images images;

        public Images getImages() {
            return images;
        }
    }

    public static class Images {
        private Original original;

        public Original getOriginal() {
            return original;
        }
    }

    public static class Original {
        private String url;

        public String getUrl() {
            return url;
        }
    }
}

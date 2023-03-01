package API_Tests;

public enum Enums {

    BASEURL("http://localhost:5000/api/books"),

    BOOKID("/11"),
    IDFORDELETE("/5"),
    BOOKWRONGID("/972fh26");

    String baseURL;

    Enums(String APIURL) {
        this.baseURL = APIURL;

    }
    public String getURL() {
        return baseURL;
    }
}



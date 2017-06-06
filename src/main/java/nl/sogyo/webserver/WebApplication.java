package nl.sogyo.webserver;

public interface WebApplication {
    void process(Request request, Response response);
}

package nl.sogyo.webserver;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResponseMessage implements Response {

    private HttpStatusCode status;
    private ZonedDateTime date;
    private String content;

    ResponseMessage(RequestMessage requestMessage){
        this.status = HttpStatusCode.OK;
        this.date = ZonedDateTime.now();
        this.content = buildContent(requestMessage);
    }

    private String buildContent(RequestMessage requestMessage){
        StringBuilder sb =  new StringBuilder("<html><body>" +
                "You did an HTTP " + requestMessage.getHTTPMethod() + " request and " +
                "you requested the following resource: " + requestMessage.getResourcePath() + "<br/>");
        List<String> headerParameterNames = requestMessage.getHeaderParameterNames();
        if(headerParameterNames.size() > 0){
            sb.append("<br/>The following header parameters were passed:<br/>");
            for(String name : headerParameterNames){
                sb.append(name + ": ");
                sb.append(requestMessage.getHeaderParameterValue(name) + "<br/>");
            }
        }
        List<String> parameterNames = requestMessage.getParameterNames();
        if(parameterNames.size() > 0){
            sb.append("<br/>The following parameters were passed:<br/>");
            for(String name : parameterNames){
                sb.append(name + ": ");
                sb.append(requestMessage.getParameterValue(name) + "<br/>");
            }
        }
        sb.append("</html></body>");
        return sb.toString();
    }

    @Override
    public String toString(){
        return  "HTTP/1.1 " + this.status.getCode() + " " + this.status.getDescription() + "\r\n" +
                "Date: " + this.date.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\r\n" +
                "Server: Sogyo desktop Roméo\r\n" +
                "Connection: close\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }

    public HttpStatusCode getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatusCode status) {}

    public LocalDate getDate() {
        return this.date.toLocalDate();
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {}
}

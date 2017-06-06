package nl.sogyo.webserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestMessage implements Request {

    private final HttpMethod httpMethod;
    private final String resourcePath;
    private final HashMap<String, String> headerParameters = new HashMap<>();
    private final HashMap<String, String> parameters = new HashMap<>();

    RequestMessage(String requestLine){
        String[] request = requestLine.split("\\s+");
        this.httpMethod = HttpMethod.valueOf(request[0]);
        if(this.getHTTPMethod() == HttpMethod.GET && request[1].contains("?")){
            String[] splitPath = request[1].split("\\?");
            this.resourcePath = splitPath[0];
            setParameters(splitPath[1]);
        }else{
            this.resourcePath = request[1];
        }
    }

    void setHeaderParameter(String headerParameterLine) {
        String[] headerParameter = headerParameterLine.split(": ");
        this.headerParameters.put(headerParameter[0], headerParameter[1]);
    }

    void setParameters(String parametersLine) {
        String[] parameters = parametersLine.split("&");
        for(String parameterLine : parameters){
            String[] parameter = parameterLine.split("=");
            this.parameters.put(parameter[0], parameter[1]);
        }
    }

    public HttpMethod getHTTPMethod() {
        return this.httpMethod;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public List<String> getHeaderParameterNames() {
        return new ArrayList<>(this.headerParameters.keySet());
    }

    public String getHeaderParameterValue(String name) {
        return this.headerParameters.get(name);
    }

    public List<String> getParameterNames() {
        return new ArrayList<>(this.parameters.keySet());
    }

    public String getParameterValue(String name) {
        return this.parameters.get(name);
    }
}

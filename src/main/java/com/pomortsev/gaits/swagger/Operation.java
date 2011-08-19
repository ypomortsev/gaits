package com.pomortsev.gaits.swagger;

public class Operation {
    public enum HttpMethod { GET, POST, PUT, DELETE }

    /**
     * A list of parameters that this method accepts
     */
    private Parameter[] parameters;

    public Parameter[] getParameters() { return parameters; }
    public void setParameters(Parameter[] parameters) { this.parameters = parameters; }

    /**
     * What type of HTTP operation this method is
     */
    private HttpMethod httpMethod = HttpMethod.GET;

    public HttpMethod getHttpMethod() { return httpMethod; }
    public void setHttpMethod(HttpMethod httpMethod) { this.httpMethod = httpMethod; }

    /**
     * An arbitrary ID or name for this method
     */
    private String nickname;

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    /**
     * A human-readable description of what this method does
     */
    private String summary;

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    /**
     * Additional documentation for the method
     */
    private String notes;

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    /**
     * The fully-qualified response model to be returned from the operation.
     * For instrumentation, this requires the full package specifier, or you can
     * specify a primitive value.
     */
    private String responseClass;

    public String getResponseClass() { return responseClass; }
    public void setResponseClass(String responseClass) { this.responseClass = responseClass; }

    /**
     * This indicates that the response contains an array of responseClass objects.
     */
    private boolean multiValueResponse = false;

    public boolean isMultiValueResponse() { return multiValueResponse; }
    public void setMultiValueResponse(boolean multiValueResponse) { this.multiValueResponse = multiValueResponse; }

    /**
     * This is a comma-separated list of tag strings which can be used to group
     * operations. Tags are optional in Swagger.
     */
    private String tags;

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    /**
     * Whether or not this operation is deprecated in the current version of the API
     */
    private boolean deprecated = false;

    public boolean isDeprecated() { return deprecated; }
    public void setDeprecated(boolean deprecated) { this.deprecated = deprecated; }

    /**
     * Whether or not this operation is "open" -- that is, if it is accessible without authentication
     */
    private boolean open = true;

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }
}

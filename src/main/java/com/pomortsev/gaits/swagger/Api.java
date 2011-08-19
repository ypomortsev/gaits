package com.pomortsev.gaits.swagger;

public class Api {
    /**
     * The URI of the API relative to the base path.
     */
    private String path;

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    /**
     * A description of what the API does.
     */
    private String description = "";

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * A list of operations for this API
     */
    private Operation[] operations;

    public Operation[] getOperations() { return operations; }
    public void setOperations(Operation[] operations) { this.operations = operations; }

    /**
     * A list of error responses that this API can return
     */
    private ErrorResponse[] errorResponses;

    public ErrorResponse[] getErrorResponses() { return errorResponses; }
    public void setErrorResponses(ErrorResponse[] errorResponses) { this.errorResponses = errorResponses; }
}

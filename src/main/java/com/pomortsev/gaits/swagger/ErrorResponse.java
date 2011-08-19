package com.pomortsev.gaits.swagger;

/**
 * Each API has a corresponding array of errorResponse objects. These describe the
 * possible error codes and what they mean to the client (note: a HTTP 500 error
 * response is always possible and implicit)
 */
public class ErrorResponse {
    /**
     * The HTTP error code returned. See http://en.wikipedia.org/wiki/List_of_HTTP_status_codes for a complete list.
     */
    private int code;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    /**
     * A human-readable reason for the error, applicable to this operation.  Note
     * that returning a 404 error response will tell you "not found" but for usability, the
     * error code should be more explicit with reasons like "user was not found" to avoid
     * confusion between general service availability and application logic.
     */
    private String reason;

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}

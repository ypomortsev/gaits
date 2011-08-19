package com.pomortsev.gaits.swagger;

public class Parameter {
    public enum ParamType {
        /**
         * The input is part of the URL itself, specifically the URL portion which
         * corresponds to the {name} in the API path.  All path parameters are mandatory and
         * must be non-zero length
         */
        PATH,
        /**
         * The input is specified by a key/value query param in the form of
         * {key}={value}. Multiple query parameters are separated by "&" delimiter
         */
        QUERY,
        /**
         * One and only one input object is supplied as part of a HTTP post operation.
         * This is the only supported mechanism for supplying complex objects to the operation
         */
        POST;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * The name of the parameter
     */
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * A description of the parameter
     */
    private String description;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * The primitive type of the allowable input values for query and path params. For post
     * params, complex datatypes are supported per the "models" section. Even though
     * query and path params are passed as part of the URI, specifying the type (Integer,
     * String) can help the client send appropriate values
     */
    private String dataType;

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * This declares the class of the model being returned from the API operation
     */
    private String responseClass;

    public String getResponseClass() { return responseClass; }
    public void setResponseClass(String responseClass) { this.responseClass = responseClass; }

    /**
     * For query parameters, this outlines the options that the client
     * can supply. The Swagger framework does not enforce that the client conforms to
     * the allowable values—that is the job of the underlying server implementation
     */
    private String[] allowableValues;

    public String[] getAllowableValues() { return allowableValues; }
    public void setAllowableValues(String[] allowableValues) { this.allowableValues = allowableValues; }

    /**
     * For query parameters, this signals to the client that multiple values
     * can be specified in a comma-separated list
     */
    private boolean allowMultiple;

    public boolean isAllowMultiple() { return allowMultiple; }
    public void setAllowMultiple(boolean allowMultiple) { this.allowMultiple = allowMultiple; }

    /**
     * Whether or not this parameter is required for every method call
     */
    private boolean required;

    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }

    /**
     * The type of the parameter: path, query, or post.
     */
    private ParamType paramType;

    public ParamType getParamType() { return paramType; }
    public void setParamType(ParamType paramType) { this.paramType = paramType; }

    /**
     * Specifies a default value (one of those specified in allowableValues) for this parameter
     */
    private String defaultValue;

    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
}

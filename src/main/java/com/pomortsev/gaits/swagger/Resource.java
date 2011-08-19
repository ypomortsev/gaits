package com.pomortsev.gaits.swagger;

/**
 * An object structure for version 1.0 of the Swagger API description format.
 *
 * At the heart of Swagger is the resource declaration.  This describes the APIs
 * provided by the system and the required and optional fields for each operation.
 * The resource declaration also provides information on the allowable inputs and
 * describes complex objects transmitted to and from the API, as well as the error
 * conditions that can occur if something goes wrong
 */
public class Resource {
    /**
     * The fixed portion of the API being declared. All API paths are relative to this basePath
     */
    private String basePath;

    public String getBasePath() { return basePath; }
    public void setBasePath(String basePath) { this.basePath = basePath; }

    /**
     * The version of the Swagger specification implemented by this provider.
     */
    private String swaggerVersion = "1.0";

    public String getSwaggerVersion() { return swaggerVersion; }
    public void setSwaggerVersion(String swaggerVersion) { this.swaggerVersion = swaggerVersion; }

    /**
     * The version of the implemented API
     */
    private String apiVersion;

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }

    /**
     * An array of APIs available to the client.
     */
    private Api[] apis;

    public Api[] getApis() { return apis; }
    public void setApis(Api[] apis) { this.apis = apis; }

    /**
     *  An array of models used in this API
     *  TODO: models
     */
    // private Model[] models;
}

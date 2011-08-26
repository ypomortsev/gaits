package com.pomortsev.gaits;

import com.apigee.api.wadl.x2010.x07.AuthenticationType;
import com.apigee.api.wadl.x2010.x07.TagType;
import com.apigee.api.wadl.x2010.x07.TagsType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pomortsev.gaits.swagger.Api;
import com.pomortsev.gaits.swagger.Operation;
import com.pomortsev.gaits.swagger.Parameter;
import com.pomortsev.gaits.swagger.Resource;
import com.pomortsev.gaits.swagger.gsonadapter.ParamTypeAdapter;
import net.java.dev.wadl.x2009.x02.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.ArrayList;

public class Converter {
    public File outputDir;

    private Gson gson;

    public Converter(File outputDir) {
        this.outputDir = outputDir;

        // init Gson instance
        this.gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Parameter.ParamType.class, new ParamTypeAdapter())
            .setPrettyPrinting()
            .create();
    }

    public Converter() {
        this(new File("."));
    }

    /**
     * Serializes the object to a JSON file
     *
     * @param obj      the object to serialize
     * @param filename the name of the file to write it to
     * @throws java.io.IOException
     */
    private void writeJson(Object obj, String filename) throws IOException {
        File file = new File(this.outputDir, filename);
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            this.gson.toJson(obj, writer);
        } finally {
            if (writer != null) writer.close();
        }
    }

    /**
     * Returns a resources.json description
     *
     * @param app the parsed WADL application object
     * @return Resource object
     */
    public Resource getResourceListing(ApplicationDocument.Application app) {
        Resource resource = new Resource();

        // set some base options

        resource.setApiVersion("1.0"); // FIXME: resource listing api version
        resource.setBasePath(""); // FIXME: resource listing base path

        // loop through every <resources> and add an Api for each

        ArrayList<Api> apis = new ArrayList<Api>(app.sizeOfResourcesArray());

        for (ResourcesDocument.Resources wadlResources : app.getResourcesArray()) {
            Api api = new Api();
            api.setPath(String.format("/%s.json", WadlUtils.getOperationSlug(wadlResources)));
            api.setDescription(wadlResources.getBase());
            apis.add(api);
        }

        resource.setApis(apis.toArray(new Api[apis.size()]));

        return resource;
    }

    /**
     * Returns an operation listing for a <resources> section of the WADL
     *
     * @param wadlResources the parsed WADL <resources> object
     * @return Resource object
     */
    public Resource getOperationListing(ResourcesDocument.Resources wadlResources) {
        return convertResources(wadlResources);
    }

    protected Resource convertResources(ResourcesDocument.Resources wadlResources) {
        Resource resource = new Resource();

        // set some base options

        resource.setApiVersion("1.0"); // FIXME: operation listing api version
        resource.setBasePath(wadlResources.getBase());

        // loop over each <resource>

        ArrayList<Api> apis = new ArrayList<Api>(wadlResources.sizeOfResourceArray());

        for (ResourceDocument.Resource wadlResource : wadlResources.getResourceArray()) {
            apis.add(convertResource(wadlResource));
        }

        resource.setApis(apis.toArray(new Api[apis.size()]));

        return resource;
    }

    protected Api convertResource(ResourceDocument.Resource wadlResource) {
        Api api = new Api();

        api.setPath(wadlResource.getPath());

        // add a description from <doc> tags
        api.setDescription(WadlUtils.getDocValue(wadlResource.getDocArray()));

        // loop over methods and create operations

        ArrayList<Operation> operations = new ArrayList<Operation>(wadlResource.sizeOfMethodArray());

        for (MethodDocument.Method wadlMethod : wadlResource.getMethodArray()) {
            operations.add(convertMethod(wadlMethod, wadlResource));
        }

        api.setOperations(operations.toArray(new Operation[operations.size()]));

        // TODO: error responses

        return api;
    }

    protected Operation convertMethod(MethodDocument.Method wadlMethod, ResourceDocument.Resource wadlResource) {
        Operation operation = new Operation();

        // the nickname is the method ID
        operation.setNickname(wadlMethod.getId());

        // http method is the method name
        operation.setHttpMethod(Operation.HttpMethod.valueOf(wadlMethod.getName()));

        // if the method requires authentication, set open to false, and vice versa
        AuthenticationType authenticationType = wadlMethod.getAuthentication();
        if (authenticationType.isSetRequired())
            operation.setOpen(!Boolean.valueOf(authenticationType.getRequired()));

        // add a summary from <doc> tags
        operation.setSummary(WadlUtils.getDocValue(wadlMethod.getDocArray()));

        // add tags

        TagsType tagsElem = wadlMethod.getTags();

        ArrayList<String> tags = new ArrayList<String>(tagsElem.sizeOfTagArray());

        for (TagType tag : wadlMethod.getTags().getTagArray()) {
            tags.add(tag.getStringValue());
        }

        // -- smush the tags together, comma separated
        String tagsStr = StringUtils.join(tags.toArray(new String[tags.size()]), ',');

        operation.setTags(tagsStr);

        // add parameters

        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        // -- add method request params
        if (wadlMethod.isSetRequest()) {
            RequestDocument.Request request = wadlMethod.getRequest();

            for (ParamDocument.Param wadlRequestParam : request.getParamArray()) {
                parameters.add(convertParam(wadlRequestParam));
            }
        }

        // -- add resource params
        for (ParamDocument.Param wadlResourceParam : wadlResource.getParamArray()) {
            parameters.add(convertParam(wadlResourceParam));
        }

        operation.setParameters(parameters.toArray(new Parameter[parameters.size()]));

        // TODO: example url?
        // TODO: responseClass

        return operation;
    }

    protected Parameter convertParam(ParamDocument.Param wadlParam) {
        Parameter parameter = new Parameter();

        parameter.setName(wadlParam.getName());

        parameter.setRequired(wadlParam.isSetFixed() || wadlParam.getRequired());

        // add a description from <doc> tags
        parameter.setDescription(WadlUtils.getDocValue(wadlParam.getDocArray()));

        // set the parameter type
        // TODO: post type, matrix?

        Parameter.ParamType paramType = Parameter.ParamType.QUERY;

        if (wadlParam.isSetStyle()) {
            ParamStyle.Enum style = wadlParam.getStyle();
            if (style == ParamStyle.TEMPLATE) {
                paramType = Parameter.ParamType.PATH;
            }
        }

        parameter.setParamType(paramType);

        // set the dataType. this only works for a small subset of the xsd: types
        // TODO: custom/complex datatypes -> models
        parameter.setDataType(wadlParam.getType().getLocalPart());

        // set the default value
        /* note: the current version of the Swagger UI doesn't support (show) arbitrary
         * default values (those not in the allowableValues list)
         */

        if (wadlParam.isSetFixed()) {
            parameter.setDefaultValue(wadlParam.getFixed());
        } else if (wadlParam.isSetDefault()) {
            parameter.setDefaultValue(wadlParam.getDefault());
        }

        // set the allowable values

        // if we have a fixed value, it's the only allowable one
        if (wadlParam.isSetFixed()) {
            parameter.setAllowableValues(new String[]{ wadlParam.getFixed() });
        } else {
            // get <option>s and set allowableValues accordingly

            ArrayList<String> allowableValues = new ArrayList<String>(wadlParam.sizeOfOptionArray());

            for (OptionDocument.Option option : wadlParam.getOptionArray()) {
                allowableValues.add(option.getValue());
            }

            parameter.setAllowableValues(allowableValues.toArray(new String[allowableValues.size()]));
        }

        return parameter;
    }

    public void convert(File wadlFile) {
        try {
            ApplicationDocument appDoc = ApplicationDocument.Factory.parse(wadlFile);
            ApplicationDocument.Application app = appDoc.getApplication();

            // create a resource listing

            Resource resourceListing = getResourceListing(app);
            writeJson(resourceListing, "resources.json");

            // create the operation listings for each <resources>

            for (ResourcesDocument.Resources wadlResources : app.getResourcesArray()) {
                Resource operationListing = getOperationListing(wadlResources);
                writeJson(operationListing, WadlUtils.getOperationSlug(wadlResources) + ".json");
            }
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

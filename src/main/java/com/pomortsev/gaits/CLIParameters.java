package com.pomortsev.gaits;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

public class CLIParameters {
    @Parameter(names = "-wadl",
           description = "WADL file to convert",
           required = true,
           converter = FileConverter.class)
    public File wadlFile = null;

    @Parameter(names = "-out",
           description = "Output directory",
           required = false,
           converter = FileConverter.class)
    public File outDir = new File(".");
}

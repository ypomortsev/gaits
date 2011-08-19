package com.pomortsev.gaits;

import com.beust.jcommander.JCommander;

public class Gaits {
    public static void main(String[] args) {
        // parse parameters

        CLIParameters params = new CLIParameters();
        JCommander jcommander = new JCommander(params, args);

        new Converter(params.outDir).convert(params.wadlFile);
    }
}

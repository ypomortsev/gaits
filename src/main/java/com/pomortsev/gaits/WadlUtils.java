package com.pomortsev.gaits;

import net.java.dev.wadl.x2009.x02.DocDocument;
import net.java.dev.wadl.x2009.x02.ResourcesDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.XmlCursor;

public class WadlUtils {
    public static String getDocValue(DocDocument.Doc[] docs) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO: trimming with newlines
        for (DocDocument.Doc doc : docs) {
            // is there a better way of doing this?
            XmlCursor cursor = doc.newCursor();
            stringBuilder.append(StringUtils.trim(cursor.getTextValue()));
            cursor.dispose();
        }

        return stringBuilder.toString();
    }

    /**
     * Returns a filename slug for a WADL <resources> section
     *
     * @param wadlResources the parsed WADL resources object
     * @return the slug
     */
    public static String getOperationSlug(ResourcesDocument.Resources wadlResources) {
        return wadlResources.getBase().replaceAll("[^a-zA-Z0-9-]+", "_");
    }
}

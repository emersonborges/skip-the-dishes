package com.skip.the.dishes.products;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class TestUtils {

    static String payload(String file) throws IOException {
        return FileUtils.readFileToString(new File(Objects.requireNonNull(ProductIntegratedTest.class.getClassLoader().getResource("payloadSample/" + file)).getFile()), "UTF-8");
    }
}

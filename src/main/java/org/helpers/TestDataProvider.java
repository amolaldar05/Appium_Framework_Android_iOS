package org.helpers;

import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public Iterator<Object[]> getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/testData/formData.json";
        List<Map<String, String>> testData = JsonUtils.getJsonDataAsList(filePath);

        return testData.stream()
                .map(data -> new Object[]{data})
                .iterator();
    }
}

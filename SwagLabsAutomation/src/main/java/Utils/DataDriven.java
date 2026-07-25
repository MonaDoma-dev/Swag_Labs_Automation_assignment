
package Utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

    public class DataDriven {

        public static JSONObject jsonReader(String filePath) {
            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(filePath)) {
                Object obj = parser.parse(reader);
                return (JSONObject) obj;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not read JSON file at path: " + filePath);
            }
        }
    }


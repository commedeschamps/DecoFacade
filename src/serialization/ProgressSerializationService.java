package serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ProgressSerializationService {
    private final ObjectMapper objectMapper;

    public ProgressSerializationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ProgressSerializationService() {
        this(new ObjectMapper());
    }

    public void exportProgress(Map<String, Integer> progress, Path targetFile) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(targetFile.toFile(), progress);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to export progress to JSON", e);
        }
    }

    public Map<String, Integer> importProgress(Path sourceFile) {
        try {
            return objectMapper.readValue(sourceFile.toFile(), new TypeReference<Map<String, Integer>>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException("Failed to import progress from JSON", e);
        }
    }
}

package gradation.implementation.businesstier.databasebackup.implementation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class MediaTypeSettingImplementation {

    public static MediaType returnForFileName(ServletContext servletContext, String fileName) {

        String mimeType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mimeType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}

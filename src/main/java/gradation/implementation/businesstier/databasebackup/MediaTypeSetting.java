package gradation.implementation.businesstier.databasebackup;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeSetting {

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

package gradation.implementation.businesstier.databasebackup.contract;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
public interface MediaTypeSetting {

    MediaType returnForFileName(ServletContext servletContext, String fileName);

}

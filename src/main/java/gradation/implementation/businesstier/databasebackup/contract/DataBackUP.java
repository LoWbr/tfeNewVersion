package gradation.implementation.businesstier.databasebackup.contract;

import java.io.IOException;

public interface DataBackUP {

    public void saveOnServer() throws IOException, InterruptedException;
    public void saveForDownload() throws IOException, InterruptedException;


}

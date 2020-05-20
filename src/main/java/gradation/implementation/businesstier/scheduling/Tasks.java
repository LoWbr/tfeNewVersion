package gradation.implementation.businesstier.scheduling;

import gradation.implementation.businesstier.databasebackup.DatabaseBackUp;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class Tasks {

    private DatabaseBackUp databaseBackUp = new DatabaseBackUp();

    @Scheduled(fixedRate = 5000)
    public void permanent() throws IOException {
        if(getStatus()){
            try {
                databaseBackUp.saveOnServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println("BackUp.");
        }
        else{
            System.out.println("Not Active for the moment");
        }
    }

    /*@Scheduled(cron = "0 0 * * * *")
    public void daily() throws IOException {
        if(getStatus()){
            databaseBackUp.saveOnServer();
        }
    }*/

    public boolean getStatus() throws IOException {
        Properties values = this.extractValue("src/main/resources/settingVariables.properties");
        if (values.get("activeScheduleBackUp").equals("yes")){
            return true;
        }
        return false;
    }

    public Properties extractValue(String file) throws IOException {
        FileInputStream fileInputStream = null;
        Properties content = new Properties();
        fileInputStream = new FileInputStream(file);
        content.load(fileInputStream);
        fileInputStream.close();
        return content;
    }
}

package gradation.implementation.businesstier.scheduling.implementation;

import gradation.implementation.businesstier.databasebackup.contract.DataBackUP;
import gradation.implementation.businesstier.scheduling.contract.SchedulingTasks;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class SchedulingImplementationTask implements SchedulingTasks {

    private DataBackUP databaseBackUp;

    @Scheduled(fixedRate = 5000)
    public void cyclicDump() throws IOException {
        if(getStatus()){
            /*try {
                databaseBackUp.saveOnServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }*/
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

    public void activeProcess(String file) throws ConfigurationException {
        PropertiesConfiguration setting = new PropertiesConfiguration(file);
        setting.setProperty("activeScheduleBackUp","yes");
        setting.save();
    }
    public void stopProcess(String file) throws ConfigurationException {
        PropertiesConfiguration setting = new PropertiesConfiguration(file);
        setting.setProperty("activeScheduleBackUp","no");
        setting.save();
    }
}

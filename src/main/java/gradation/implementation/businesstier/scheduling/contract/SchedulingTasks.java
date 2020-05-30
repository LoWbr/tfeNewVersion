package gradation.implementation.businesstier.scheduling.contract;

import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;

public interface SchedulingTasks {

    public void cyclicDump() throws IOException;

    public boolean getStatus() throws IOException;

    public void activeProcess(String file) throws ConfigurationException;

    public void stopProcess(String file) throws ConfigurationException;

}

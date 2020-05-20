package gradation.implementation.businesstier.databasebackup;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class DatabaseBackUp{

    private final String pathForTemporaryBackUp = "/home/laurent/ultimateProjects/phase3/tfe_implementation/backupForDownload";
    private final String pathForCyclicBackUp = "/home/laurent/ultimateProjects/phase3/tfe_implementation/backup";
    public void saveOnServer() throws IOException, InterruptedException {
        LocalDateTime currentTiming = LocalDateTime.now();
        String fileName = "DB_Backup_" + currentTiming;
        String dbName = "tfe";
        File f1 = new File(pathForCyclicBackUp);
        f1.mkdir();

        String saveFileName = fileName + ".sql";
        String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbName
                + " -r " + savePath;
        Process runtimeProcess = null;
        runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = 0;
        processComplete = runtimeProcess.waitFor();
        //Log à mettre en place!!
    }

    public void saveForDownload() throws IOException, InterruptedException {
/*
        this.checkEmptyRepository(pathForTemporaryBackUp);
*/
        String dbName = "tfe";
        String fileName = "Gradation_DB_BackUp";
        File f1 = new File(pathForTemporaryBackUp);
        f1.mkdir();

        String saveFileName = fileName + ".sql";
        String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbName
                + " -r " + savePath;
        Process runtimeProcess = null;
        runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = 0;
        processComplete = runtimeProcess.waitFor();
        //Log à mettre en place!!
    }

    private void checkEmptyRepository(String pathForTemporaryBackUp) {
        /*File repository = new File(pathForTemporaryBackUp);
        if (repository.listFiles().length > 0){
            File[] files = repository.listFiles();
            for (File file:files) {
                file.delete();
            }
        }*/
    }

}

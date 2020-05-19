package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ManagementService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.PromotionRequestRepository;
import gradation.implementation.datatier.repositories.TopicRepository;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.LevelForm;
import gradation.implementation.presentationtier.form.TopicForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class ManagementServiceImplementation implements ManagementService {

    private PromotionRequestRepository promotionRequestRepository;
    private TopicRepository topicRepository;

    private NewsService newsService;
    private RoleService roleService;

    @Autowired
    public ManagementServiceImplementation(PromotionRequestRepository promotionRequestRepository,
                                           TopicRepository topicRepository, NewsService newsService,
                                           RoleService roleService ){
        this.promotionRequestRepository = promotionRequestRepository;
        this.topicRepository = topicRepository;
        this.newsService = newsService;
        this.roleService = roleService;
    }

    @Override
    public void saveRequest(PromotionRequest promotionRequest) {
        this.promotionRequestRepository.save(promotionRequest);
    }

    @Override
    public PromotionRequest findSpecific(SportsMan sportsMan) {
        return this.promotionRequestRepository.findByCandidate(sportsMan);
    }

    @Override
    public void removeRequest(PromotionRequest promotionRequest) {
        this.promotionRequestRepository.delete(promotionRequest);
    }

    @Override
    public List<SportsMan> getPromotionCandidates() {
        List<SportsMan> candidates = new ArrayList<>();
        Iterable<PromotionRequest> allPromotions = this.promotionRequestRepository.findAll();
        for (PromotionRequest promotion: allPromotions) {
            candidates.add(promotion.getCandidate());
        }
        return candidates;
    }

    @Override
    public void addTopic(SportsMan sportsMan, TopicForm topicForm) {
        Topic topic = new Topic(sportsMan, topicForm);
        this.topicRepository.save(topic);
    }

    @Override
    public void getDBStatus() {
        System.out.println("Backup Started at " + new Date());

        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String backupDateStr = format.format(backupDate);
        String dbNameList = "tfe";

        String fileName = "Daily_DB_Backup"; // default file name
        String folderPath = "/home/laurent/ultimateProjects/phase3/tfe_repo";
        File f1 = new File(folderPath);
        f1.mkdir(); // create folder if not exist

        String saveFileName = fileName + /*"_" + backupDateStr + */".sql";
        String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbNameList
                + " -r " + savePath;

        Process runtimeProcess = null;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int processComplete = 0;
        try {
            processComplete = runtimeProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (processComplete == 0) {
            System.out.println("Backup Complete at " + new Date());
        } else {
            System.out.println("Backup Failure");
        }
    }

    @Override
    public void applyForConfirmedRole(SportsMan sportsMan) {
        PromotionRequest promotionRequest = new PromotionRequest(sportsMan,roleService.findConfirmedRole());
        this.saveRequest(promotionRequest);
        newsService.returnApplicationNew(sportsMan,NewsType.APPLY_AS_CONFIRMED);
    }

    @Override
    public void returnDB() {
        System.out.println("Backup Started at " + new Date());

        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String backupDateStr = format.format(backupDate);
        String dbNameList = "tfe";

        String fileName = "Daily_DB_Backup"; // default file name
        String folderPath = "/home/laurent/ultimateProjects/phase3/tfe_implementation";
        File f1 = new File(folderPath);
        f1.mkdir(); // create folder if not exist

        String saveFileName = fileName + /*"_" + backupDateStr + */".sql";
        String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

        String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbNameList
                + " -r " + savePath;

        Process runtimeProcess = null;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int processComplete = 0;
        try {
            processComplete = runtimeProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (processComplete == 0) {
            System.out.println("Backup Complete at " + new Date());
        } else {
            System.out.println("Backup Failure");
        }
    }


}

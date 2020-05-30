package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.ActivityTypeRepository;
import gradation.implementation.datatier.repositories.AddressRepository;
import gradation.implementation.datatier.repositories.CommentRepository;
import gradation.implementation.datatier.repositories.LevelRepository;
import gradation.implementation.presentationtier.form.ActivityForm;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.CommentForm;
import gradation.implementation.presentationtier.form.LevelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class ActivitySettingServiceImplementation implements ActivitySettingService {

    private LevelRepository levelRepository;
    private CommentRepository commentRepository;
    private ActivityTypeRepository activityTypeRepository;
    private AddressRepository addressRepository;

    @Autowired
    public ActivitySettingServiceImplementation(LevelRepository levelRepository, CommentRepository commentRepository,
                                                ActivityTypeRepository activityTypeRepository,AddressRepository
                                                            addressRepository){
        this.levelRepository = levelRepository;
        this.commentRepository = commentRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Level findSpecificLevel(Long id) {
        return this.levelRepository.findSpecific(id);
    }

    @Override
    public Level findBeginner() {
        return this.levelRepository.findSpecific((long) 1);
    }

    @Override
    public Level findLevelByPlace(Byte place) {
        return this.levelRepository.findSpecific(place);
    }

    @Override
    public CommentForm initiateCommentForm(Activity activity, SportsMan sportsMan) {
        CommentForm commentForm = new CommentForm(sportsMan, activity);
        return commentForm;
    }

    @Override
    public void createComment(CommentForm commentForm) {
        Comment comment = new Comment(commentForm);
        this.commentRepository.save(comment);
    }

    @Override
    public Iterable<ActivityType> getAllActivityTypes() {
        return this.activityTypeRepository.findAll();
    }

    @Override
    public ActivityType findSpecificActivityType(Long id) {
        return this.activityTypeRepository.findSpecific(id);
    }

    @Override
    public void createType(ActivityTypeForm activityTypeForm) {
        ActivityType activityType = new ActivityType();
        activityType.update(activityTypeForm);
        this.activityTypeRepository.save(activityType);
    }

    @Override
    public void updateType(ActivityType activityType, ActivityTypeForm activityTypeForm) {
        activityType.update(activityTypeForm);
        this.activityTypeRepository.save(activityType);
    }


    @Override
    public Iterable<Level> getAllLevels() {
        return this.levelRepository.findAll();
    }

    @Override
    public void updateLevel(LevelForm levelForm, Level level) {
        level.update(levelForm);
        this.levelRepository.save(level);
    }

    @Override
    public Iterable<Comment> findCommentsForActivity(Activity activity) {
        return this.commentRepository.findForEvent(activity);
    }

    @Override
    public Address createAddress(ActivityForm activityForm) {
        Address address = new Address(activityForm);
        this.addressRepository.save(address);
        return address;
    }

    @Override
    public void updateAddress(Address address, ActivityForm activityForm) {
        address.update(activityForm);
        addressRepository.save(address);
    }

    @Override
    public Address findSpecificAddress(Activity activity) {
       return this.addressRepository.findSpecific(activity.getAddress().getId());
    }
}

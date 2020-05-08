package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityForm;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.CommentForm;
import gradation.implementation.presentationtier.form.LevelForm;

public interface ActivitySettingService {

    Level findSpecificLevel(Long id);

    Level findBeginner();

    CommentForm initiateCommentForm(Activity activity, SportsMan sportsMan);

    void createComment(Comment comment);

    Iterable<ActivityType> getAllActivityTypes();

    ActivityType findSpecificActivityType(Long id);

    void createType(ActivityTypeForm activityTypeForm);

    void updateType(ActivityType activityType, ActivityTypeForm activityTypeForm);

    Iterable<Level> getAllLevels();

    void updateLevel(LevelForm levelForm, Level level);

    Iterable<Comment> findCommentsForActivity(Activity activity);

    Address createAddress(ActivityForm activityForm);

    void updateAddress(Address address, ActivityForm activityForm);

    Address findSpecificAddress(Activity activity);
}

package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.*;

public class SearchActivityForm {

    private ActivityType activity;
    private Level minimumLevel;

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }
}

package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.*;

import javax.validation.constraints.Size;

public class CommentForm {

    @Size(max=150, message="Maximum 150 characters")
    private String content;
    private SportsMan author;
    private Activity activity;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SportsMan getAuthor() {
        return author;
    }

    public void setAuthor(SportsMan author) {
        this.author = author;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public CommentForm(SportsMan author, Activity activity) {
        this.author = author;
        this.activity = activity;
    }
}

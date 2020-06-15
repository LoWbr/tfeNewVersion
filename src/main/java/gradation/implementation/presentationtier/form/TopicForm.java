package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicForm {

    @NotBlank(message = "Enter a mail")
    @Size(max=200, message="Maximum 200 characters")
    private String content;
    private SportsMan author;

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
}

package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.SportsMan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class MessageForm {

    @Size(max=50, message="Between 10 and 50 characters")
    @Size(max=10, message="Between 10 and 50 characters")
    @NotBlank(message = "Enter a subject")
    private String about;

    private SportsMan originator;

    private List<SportsMan> addressee = new ArrayList<>();

    @NotBlank(message = "Enter a content")
    private String content;

    public MessageForm(SportsMan originator, SportsMan addressee) {
        this.originator = originator;
        this.addressee.add(addressee);
    }

    public MessageForm(SportsMan originator) {
        this.originator = originator;
    }

    public MessageForm(){}

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public SportsMan getOriginator() {
        return originator;
    }

    public void setOriginator(SportsMan originator) {
        this.originator = originator;
    }

    public List<SportsMan> getAddressee() {
        return addressee;
    }

    public void setAddressee(List<SportsMan> addressee) {
        this.addressee = addressee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

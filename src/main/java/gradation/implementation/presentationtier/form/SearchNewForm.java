package gradation.implementation.presentationtier.form;


import gradation.implementation.datatier.entities.NewsType;

public class SearchNewForm {

    private String nameSportsman;

    private NewsType newsType;

    public String getNameSportsman() {
        return nameSportsman;
    }

    public void setNameSportsman(String nameSportsman) {
        this.nameSportsman = nameSportsman;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}

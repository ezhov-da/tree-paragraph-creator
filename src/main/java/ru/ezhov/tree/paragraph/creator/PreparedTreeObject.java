package ru.ezhov.tree.paragraph.creator;

import java.util.logging.Logger;

/**
 * @author ezhov_da
 */
public class PreparedTreeObject {
    private static final Logger LOG = Logger.getLogger(PreparedTreeObject.class.getName());
    private String paragraph;
    private String fullText;
    private int lvl;
    private boolean findParagraph;

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean isFindParagraph() {
        return findParagraph;
    }

    public void setFindParagraph(boolean findParagraph) {
        this.findParagraph = findParagraph;
    }

    @Override
    public String toString() {
        return "PreparedTreeObject{" +
                "paragraph='" + paragraph + '\'' +
                ", fullText='" + fullText + '\'' +
                ", lvl=" + lvl +
                ", findParagraph=" + findParagraph +
                '}';
    }

    public String getParentParagraph() {
        switch (lvl) {
            case 0:
                return null;
            case 1:
                return null;
            case 2:
                return paragraph.substring(0, 2);
            default:
                return paragraph.substring(0, paragraph.length() - 2);
        }
    }
}

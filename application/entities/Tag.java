package application.entities;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    private String tagName;
    private String value;
    private Integer level = 0;
    private List<Tag> childrenTag = new ArrayList<>();

    public Tag(){}

    public Tag(String tagName, String value,  Integer level, List<Tag> childrenTag) {
        this.tagName = tagName;
        this.value = value;
        this.level = level;
        this.childrenTag = childrenTag;
    }

    public List<Tag> getChildrenTag() {
        return childrenTag;
    }


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addTag(Tag tag){
        this.childrenTag.add(tag);
    }

}

package bean;

public class AssementReviewBean {


    /**
     * isRecord : 0
     * pcdiIsRemind : 1
     * IsRemind : 2
     * abcIsRemind : 1
     * remindType : 1
     */

    private String isRecord;
    private String pcdiIsRemind;
    private String IsRemind;
    private String abcIsRemind;
    private String remindType;

    public String getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    public String getPcdiIsRemind() {
        return pcdiIsRemind;
    }

    public void setPcdiIsRemind(String pcdiIsRemind) {
        this.pcdiIsRemind = pcdiIsRemind;
    }

    public String getIsRemind() {
        return IsRemind;
    }

    public void setIsRemind(String IsRemind) {
        this.IsRemind = IsRemind;
    }

    public String getAbcIsRemind() {
        return abcIsRemind;
    }

    public void setAbcIsRemind(String abcIsRemind) {
        this.abcIsRemind = abcIsRemind;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }
}

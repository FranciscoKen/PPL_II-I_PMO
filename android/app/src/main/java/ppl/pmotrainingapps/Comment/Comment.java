package ppl.pmotrainingapps.Comment;


public class Comment {

    public int id;
    public String contentType;
    public int contentId;
    public int userId;
    public String userName;
    public String comment;
    public String tanggal;
    public Comment(int id, String contentType, int contentId, int userId, String userName, String comment, String tanggal) {
        this.id = id;
        this.contentType = contentType;
        this.contentId = contentId;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.tanggal = tanggal;
    }
}

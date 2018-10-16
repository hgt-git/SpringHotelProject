package jProject.models;

import javax.persistence.*;

@Entity()
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String imageCurrentName;
    private String imageOriginalName;
    private String fileType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RoomType room;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageCurrentName() {
        return imageCurrentName;
    }

    public void setImageCurrentName(String imageCurrentName) {
        this.imageCurrentName = imageCurrentName;
    }

    public String getImageOriginalName() {
        return imageOriginalName;
    }

    public void setImageOriginalName(String imageOriginalName) {
        this.imageOriginalName = imageOriginalName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public RoomType getRoomId() { return room; }

    public void setRoomId(RoomType room) { this.room = room; }
}

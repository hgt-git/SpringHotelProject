package jProject.forms;


import jProject.models.RoomType;

public class ImgForm  {


    private String fileType;
    private String imageCurrentName;
    private String imageOriginalName;
    private RoomType roomId;


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

    public String getFileType() { return fileType; }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public RoomType getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomType roomId) {
        this.roomId = roomId;
    }
}

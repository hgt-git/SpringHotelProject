package jProject.services;

import jProject.forms.ImgForm;
import jProject.models.Image;
import jProject.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImageRepository imgRepository;


    @Override
    public String addNewImg(ImgForm file) {
        Image img = new Image();
        img.setImageCurrentName(file.getImageCurrentName());
        img.setImageOriginalName(file.getImageOriginalName());
        img.setFileType(file.getFileType());
        img.setRoomId(file.getRoomId());

        imgRepository.save(img);
        return "Saved";
    }

    @Override
    public String imageId(){
        int fname = 0;
        if(imgRepository.imgLastId()!=null)fname +=imgRepository.imgLastId();

        return String.valueOf(fname+1);
    }



}

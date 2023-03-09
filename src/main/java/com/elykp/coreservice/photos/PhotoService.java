package com.elykp.coreservice.photos;

import com.elykp.coreservice.photos.dto.CreatePhotoRQ;
import com.elykp.coreservice.photos.dto.PhotoRS;

public interface PhotoService {

  PhotoRS createPhoto(CreatePhotoRQ createPhotoRQ, String userId);
}

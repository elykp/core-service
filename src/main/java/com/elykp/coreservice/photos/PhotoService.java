package com.elykp.coreservice.photos;

import com.elykp.coreservice.photos.domain.CreatePhotoRQ;
import com.elykp.coreservice.photos.domain.PhotoRS;

public interface PhotoService {

  PhotoRS createPhoto(CreatePhotoRQ createPhotoRQ, String userId);

  PhotoRS getById(String id);
}

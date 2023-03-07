package com.elykp.coreservice.photos;

import com.elykp.coreservice.photos.dto.CreatePhotoDto;

public interface PhotoService {

  Photo createPhoto(CreatePhotoDto createPhotoDto, String userId);
}

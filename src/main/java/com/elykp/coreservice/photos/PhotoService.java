package com.elykp.coreservice.photos;


public interface PhotoService {

  Photo createPhoto(CreatePhotoDto createPhotoDto, String userId);
}

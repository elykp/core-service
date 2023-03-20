package com.elykp.coreservice.photos;

import com.elykp.coreservice.assets.AssetService;
import com.elykp.coreservice.photos.domain.CreatePhotoRQ;
import com.elykp.coreservice.photos.domain.PhotoRS;
import com.elykp.coreservice.photos.mapper.PhotoMapper;
import com.elykp.coreservice.shared.service.HelperService;
import com.elykp.coreservice.tags.Tag;
import com.elykp.coreservice.tags.TagRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

  private final PhotoRepository photoRepository;

  private final TagRepository tagRepository;

  private final AssetService assetService;

  @Override
  @Transactional
  public PhotoRS createPhoto(CreatePhotoRQ createPhotoRQ, String userId) {
    Photo photo = new Photo();
    photo.setId(HelperService.generateNanoId());
    photo.setTitle(createPhotoRQ.getTitle());
    photo.setBlurhash(createPhotoRQ.getBlurhash());
    photo.setNsfw(createPhotoRQ.getNsfw() != null ? createPhotoRQ.getNsfw() : false);
    photo.setLikeCount(0);
    photo.setCreatorId(userId);

    for (String tag : createPhotoRQ.getTags()) {
      Optional<Tag> existingTag = tagRepository.findByNameIgnoreCase(tag);
      if (existingTag.isPresent()) {
        photo.getTags().add(existingTag.get());
      } else {
        Tag newTag = new Tag();
        newTag.setName(tag);
        photo.getTags().add(tagRepository.save(newTag));
      }
    }

    return PhotoMapper.INSTANCE.mapPhotoToPhotoRS(photoRepository.save(photo));
  }

  @Override
  public PhotoRS getById(String id) {
    Photo photo = photoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND));
    var assets = assetService.getByPhotoId(photo.getId());
    photo.setAssets(assets);
    return PhotoMapper.INSTANCE.mapPhotoToPhotoRS(photo);
  }
}

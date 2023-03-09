package com.elykp.coreservice.photos;

import com.elykp.coreservice.assets.Asset;
import com.elykp.coreservice.assets.AssetRepository;
import com.elykp.coreservice.photos.dto.CreatePhotoRQ;
import com.elykp.coreservice.photos.dto.PhotoRS;
import com.elykp.coreservice.photos.mapper.PhotoMapper;
import com.elykp.coreservice.shared.services.HelperService;
import com.elykp.coreservice.tags.Tag;
import com.elykp.coreservice.tags.TagRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

  private final HelperService helperService;

  private final PhotoRepository photoRepository;

  private final TagRepository tagRepository;

  private final AssetRepository assetRepository;

  @Override
  @Transactional
  public PhotoRS createPhoto(CreatePhotoRQ createPhotoRQ, String userId) {
    Photo photo = new Photo();
    photo.setId(helperService.generateNanoId());
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

    Asset asset = new Asset();
    asset.setWidth(100);
    asset.setHeight(100);
    asset.setOwnerId(userId);
    asset.setUrl("/test");
    asset.setResponsiveKey("image100");
    asset.setPhoto(photoRepository.save(photo));

    photo.getAssets().add(assetRepository.save(asset));

    return PhotoMapper.INSTANCE.mapPhotoToPhotoRS(photoRepository.save(photo));
  }
}

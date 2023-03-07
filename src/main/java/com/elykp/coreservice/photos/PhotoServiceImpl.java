package com.elykp.coreservice.photos;

import com.elykp.coreservice.assets.Asset;
import com.elykp.coreservice.assets.AssetRepository;
import com.elykp.coreservice.photos.dto.CreatePhotoDto;
import com.elykp.coreservice.shared.HelperService;
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
  public Photo createPhoto(CreatePhotoDto createPhotoDto, String userId) {
    Photo photo = new Photo();
    photo.setId(helperService.generateNanoId());
    photo.setTitle(createPhotoDto.getTitle());
    photo.setBlurhash(createPhotoDto.getBlurhash());
    photo.setNsfw(createPhotoDto.getNsfw() != null ? createPhotoDto.getNsfw() : false);
    photo.setLikeCount(0);
    photo.setCreatorId(userId);

    for (String tag : createPhotoDto.getTags()) {
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
    asset.setPath("/test");
    asset.setPhoto(photoRepository.save(photo));

    photo.getAssets().add(assetRepository.save(asset));

    return photoRepository.save(photo);
  }
}

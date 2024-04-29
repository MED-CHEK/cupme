package com.cupme.service;

import com.cupme.domain.Picture;
import com.cupme.domain.Product;
import com.cupme.repository.PictureRepository;
import com.cupme.repository.ProductRepository;
import com.cupme.service.dto.ProductCartDTO;
import com.cupme.service.dto.ProductDTO;
import com.cupme.service.mapper.PictureMapper;
import com.cupme.service.mapper.ProductMapper;
import com.cupme.service.utils.AssetFilesService;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing products.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final PictureRepository pictureRepository;

    private final PictureMapper pictureMapper;
    private final AssetFilesService assetFilesService;
    private final CacheManager cacheManager;

    public ProductService(
        ProductRepository productRepository,
        ProductMapper productMapper,
        PictureRepository pictureRepository,
        PictureMapper pictureMapper,
        AssetFilesService assetFilesService,
        CacheManager cacheManager
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
        this.assetFilesService = assetFilesService;
        this.cacheManager = cacheManager;
    }

    public List<ProductCartDTO> getProducts() {
        List<ProductCartDTO> productCartDTOs = productMapper.productsToProductCartDTO(productRepository.findAll());
        productCartDTOs.forEach(productDTO -> {
            productDTO.getPicture().setFile(assetFilesService.getFile(productDTO.getPicture().getFile()));
        });

        return productCartDTOs;
    }

    public ProductDTO getProduct(long id) {
        ProductDTO productDTO = productMapper.productToProductDTO(productRepository.findById(id).get());
        productDTO.getPictures().forEach(pictureDTO -> pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile())));
        return productDTO;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        ProductDTO dto = productMapper.productToProductDTO(productRepository.save(product));

        assetFilesService.addFolder(dto.getId() + "");

        if (productDTO.getPictures() != null && productDTO.getPictures().size() > 0) {
            productDTO
                .getPictures()
                .forEach(picture -> {
                    if (picture.getMain()) {
                        picture.setName("main");
                    }
                    picture.setFile(assetFilesService.savePicture(picture, dto.getId()));
                    Picture toPicture = pictureMapper.pictureDtoToPicture(picture);
                    toPicture.setProduct(product);
                    toPicture.setFile("content/images/" + dto.getId() + "/" + picture.getName());
                    pictureRepository.save(toPicture);
                });
        }
        return dto;
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setLastModifiedDate(Instant.now());
        ProductDTO dto = productMapper.productToProductDTO(productRepository.save(product));

        pictureRepository.deleteAllByProductId(dto.getId());

        if (productDTO.getPictures() != null && productDTO.getPictures().size() > 0) {
            productDTO
                .getPictures()
                .forEach(picture -> {
                    if (picture.getMain()) {
                        picture.setName("main");
                    }
                    picture.setFile(assetFilesService.savePicture(picture, productDTO.getId()));
                    Picture toPicture = pictureMapper.pictureDtoToPicture(picture);
                    toPicture.setProduct(product);
                    toPicture.setFile("content/images/" + productDTO.getId() + "/" + picture.getName());
                    pictureRepository.save(toPicture);
                });
        }
        return dto;
    }

    public void deleteProduct(long id) {
        pictureRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }
}

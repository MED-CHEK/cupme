package com.cupme.service.utils;

import com.cupme.service.dto.PictureDTO;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssetFilesService {

    private final Logger log = LoggerFactory.getLogger(AssetFilesService.class);

    public String RESOURCESDIR = "resources";

    public AssetFilesService() {
        RESOURCESDIR = "src/main/webapp/content/images/";
    }

    public void addFolder(String folderName) {
        File folder = new File("src/main/webapp/content/images/" + folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public String savePicture(PictureDTO picture, Long assetId) {
        return saveFile(assetId, picture.getName(), picture.getFile());
    }

    private String saveFile(Long assetId, String fileName, String content) {
        if (content != null) {
            String outputFileName = RESOURCESDIR + "/" + assetId + "/" + "/" + fileName;
            File convertFile = new File(outputFileName);
            try {
                convertFile.createNewFile();
                content = content.substring(content.indexOf(",") + 1);
                byte[] decodedBytes = Base64.getDecoder().decode(content);
                FileUtils.writeByteArrayToFile(convertFile, decodedBytes);
                return convertFile.getCanonicalPath();
            } catch (IOException e) {
                log.error("Error while saving file: " + outputFileName);
            }
        }
        return null;
    }

    public String getFile(String path) {
        String filePath = "src/main/webapp/" + path;
        File convertFile = new File(filePath);
        byte[] fileContent = new byte[0];
        try {
            convertFile.createNewFile();
            fileContent = FileUtils.readFileToByteArray(convertFile);
        } catch (IOException e) {
            log.debug("File not found: " + filePath);
        }
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
}

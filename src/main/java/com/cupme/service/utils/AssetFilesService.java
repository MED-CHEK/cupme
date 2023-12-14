package com.cupme.service.utils;

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

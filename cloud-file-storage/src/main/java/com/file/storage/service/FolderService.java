package com.file.storage.service;

import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.MinioObjectDto;
import com.file.storage.dto.folder.FolderDeleteRequest;
import com.file.storage.dto.folder.FolderRenameRequest;
import com.file.storage.dto.folder.FolderUploadRequest;
import com.file.storage.exception.folder.FolderOperationException;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.file.storage.util.MinioRootFolderUtils.getUserRootFolderPrefix;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FileService fileService;
    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;

    public void uploadFolder(FolderUploadRequest folderUploadRequest) {
        List<MultipartFile> files = folderUploadRequest.getFiles();
        String owner = folderUploadRequest.getOwner();
        try {
            List<SnowballObject> objects = convertToUploadObjects(files, owner);

            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .objects(objects)
                    .build());
        }
        catch (Exception e) {
            throw new FolderOperationException("There is an error while uploading the folder, try again later");
        }
    }

    public void renameFolder(FolderRenameRequest folderRenameRequest) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(folderRenameRequest.getOwner(), folderRenameRequest.getPath());

        for (MinioObjectDto file : files) {
            try {
                minioClient.copyObject(CopyObjectArgs.builder()
                        .bucket(minioBucketConfiguration.getBucketName())
                        .object(getPathWithNewFolder(
                                getUserRootFolderPrefix(file.getOwner()) + file.getPath(),
                                folderRenameRequest.getCurrentName(),
                                folderRenameRequest.getNewName()
                        ))
                        .source(CopySource.builder()
                                .bucket(minioBucketConfiguration.getBucketName())
                                .object(getUserRootFolderPrefix(file.getOwner()) + file.getPath())
                                .build())
                        .build());

                FolderDeleteRequest folderDeleteRequest = new FolderDeleteRequest();

                folderDeleteRequest.setPath(folderRenameRequest.getPath());
                folderDeleteRequest.setOwner(folderRenameRequest.getOwner());

                deleteFolder(folderDeleteRequest);
            }
            catch (Exception e) {
                throw new FolderOperationException("There is an error while renaming the folder, try again later");
            }
        }
    }

    public void deleteFolder(FolderDeleteRequest folderDeleteRequest) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(folderDeleteRequest.getOwner(), folderDeleteRequest.getPath());

        List<DeleteObject> objects = convertToDeleteObjects(files);

        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .objects(objects)
                .build());

        // Actual deletion is performed when iterating over results
        results.forEach(deleteErrorResult -> {
            try {
                deleteErrorResult.get();
            }
            catch (Exception e) {
                throw new FolderOperationException("There is an error while deleting the folder, try again later");
            }
        });
    }

    private List<DeleteObject> convertToDeleteObjects(List<MinioObjectDto> files) {
        List<DeleteObject> objects = new ArrayList<>();

        for (MinioObjectDto file : files) {
            objects.add(new DeleteObject(getUserRootFolderPrefix(file.getOwner()) + file.getPath()));
        }

        return objects;
    }

    private List<SnowballObject> convertToUploadObjects(List<MultipartFile> files, String owner) throws IOException {
        List<SnowballObject> objects = new ArrayList<>();

        for (MultipartFile file : files) {
            // Empty file names will produce undeleteable bucket-named files
            if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
                continue;
            }

            SnowballObject snowballObject = new SnowballObject(
                    getUserRootFolderPrefix(owner) + file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    null
            );
            objects.add(snowballObject);
        }

        return objects;
    }

    private static String getPathWithNewFolder(String path, String oldName, String newName) {
        oldName = "/" + oldName + "/";
        newName = "/" + newName + "/";
        return path.replace(oldName, newName);
    }
}

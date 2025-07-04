package com.file.storage.service;

import com.file.storage.dto.MinioObjectDto;
import com.file.storage.dto.folder.FolderDeleteRequest;
import com.file.storage.dto.folder.FolderRenameRequest;
import com.file.storage.dto.folder.FolderUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("dev")
class FolderServiceIntegrationTest {

    private static final DockerImageName MINIO_IMAGE = DockerImageName.parse("quay.io/minio/minio");

    @Container
    private static final GenericContainer<?> minio = new GenericContainer<>(MINIO_IMAGE)
            .withCommand("server /data")
            .withEnv("MINIO_ROOT_USER", "user")
            .withEnv("MINIO_ROOT_PASSWORD", "minio_password")
            .withExposedPorts(9000);

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Test
    void uploadFolder_saveFolderToMinio() {
        MockMultipartFile file = getMockFileWithName("folder/File in folder");
        List<MultipartFile> folder = new ArrayList<>();
        folder.add(file);

        folderService.uploadFolder(new FolderUploadRequest("user", folder));

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        long folders = files.stream()
                .filter(MinioObjectDto::getIsDir)
                .filter(f -> f.getName().equals("folder"))
                .count();
        assertEquals(1, folders);
    }

    @Test
    void renameFolder_renameFolderInMinio() {
        MockMultipartFile file = getMockFileWithName("folder to rename/File in folder");
        List<MultipartFile> folder = new ArrayList<>();
        folder.add(file);
        folderService.uploadFolder(new FolderUploadRequest("user", folder));

        folderService.renameFolder(new FolderRenameRequest(
                "user",
                "folder to rename",
                "renamed folder",
                "folder to rename")
        );

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        long foldersWithOldName = files.stream()
                .filter(MinioObjectDto::getIsDir)
                .filter(f -> f.getName().equals("folder to rename"))
                .count();
        long foldersWithNewName = files.stream()
                .filter(MinioObjectDto::getIsDir)
                .filter(f -> f.getName().equals("renamed folder"))
                .count();
        assertEquals(0, foldersWithOldName);
        assertEquals(1, foldersWithNewName);
    }

    @Test
    void deleteFolder_deleteFolderInMinio() {
        MockMultipartFile file = getMockFileWithName("folder to delete/File in folder");
        List<MultipartFile> folder = new ArrayList<>();
        folder.add(file);
        folderService.uploadFolder(new FolderUploadRequest("user", folder));

        folderService.deleteFolder(new FolderDeleteRequest("user", "folder to delete"));

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        long folders = files.stream()
                .filter(MinioObjectDto::getIsDir)
                .filter(f -> f.getName().equals("folder to delete"))
                .count();
        assertEquals(0, folders);
    }

    @DynamicPropertySource
    static void minioProperties(DynamicPropertyRegistry registry) {
        registry.add("minio.client.endpoint", () -> "http://127.0.0.1:" + minio.getMappedPort(9000));
        registry.add("minio.client.user", () -> "user");
        registry.add("minio.client.password", () -> "minio_password");
        registry.add("minio.bucket-name", () -> "user-files");
    }

    private static MockMultipartFile getMockFileWithName(String name) {
        return new MockMultipartFile(
                "name",
                name,
                "text/plain",
                "Content".getBytes(StandardCharsets.UTF_8)
        );
    }
}

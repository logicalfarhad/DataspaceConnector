package io.dataspaceconnector.controller.arx;

import io.dataspaceconnector.controller.arx.util.FileInfo;
import io.dataspaceconnector.controller.arx.util.FilesStorageService;
import io.dataspaceconnector.controller.arx.util.ResponseMessage;
import io.dataspaceconnector.controller.arx.util.SomeForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Anonymize", description = "Endpoints for ARX Anonymizer")
public class ArxController {
    private final FilesStorageService _storageService;

    @Autowired
    public ArxController(FilesStorageService storageService) {
        this._storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(SomeForm file) {
        String message;
        var _file = file.getMf();
        try {
            _storageService.save(file.getMf());
            message = "Uploaded the file successfully: " + _file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + _file.getOriginalFilename() + "!";
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        var fileInfos = _storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ArxController.class,
                            "getFile",
                            path.getFileName().toString()).build().toString();
            return new FileInfo(filename,url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/getfileContent/{count}")
    @ResponseBody
    public ResponseEntity<List<String>> getFileContent(@PathVariable int count) {
        var fileContent = _storageService.getLines(count);
        return ResponseEntity.status(HttpStatus.OK).body(fileContent);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = _storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}



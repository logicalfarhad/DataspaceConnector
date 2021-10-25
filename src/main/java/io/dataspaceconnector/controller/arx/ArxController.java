package io.dataspaceconnector.controller.arx;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Tag(name = "Anonymize", description = "Endpoints for ARX Anonymizer")
public class ArxController {
    private final Path root = Paths.get("uploads");

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseMessage> uploadFile(SomeForm form) {
        String message;
        try {
            if (!Files.isDirectory(root)) {
                Files.createDirectory(root);
            }
            var file = form.getMf();
            if (file != null) {
                var exists = Files.exists(root.resolve(file.getOriginalFilename()));
                if(exists)
                    Files.delete(root.resolve(file.getOriginalFilename()));
                Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } else {
                message = "File not found!";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
            }
        } catch (Exception e) {
            message = "Could not upload the file: " + form.getMf().getOriginalFilename() + "!";
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
}

@Data
class SomeForm {
    private MultipartFile mf;
    private String str;
}



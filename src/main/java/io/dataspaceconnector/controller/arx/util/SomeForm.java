package io.dataspaceconnector.controller.arx.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SomeForm {
    private MultipartFile mf;
    private String str;
}

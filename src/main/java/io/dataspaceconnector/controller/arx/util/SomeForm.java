package io.dataspaceconnector.controller.arx.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SomeForm {
    private MultipartFile mf;
    private String str;
}

package com.neonzoff.DemoSpringMVC.controller;

/**
 * @author Tseplyaev Dmitry
 */

import java.io.*;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("file")
public class FileUploadController {
    private StringBuffer linesOfFile;

    @PostMapping()
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream()));
                 BufferedOutputStream stream = new BufferedOutputStream(
                         new FileOutputStream(UUID.randomUUID() + file.getOriginalFilename()));) {
                linesOfFile = new StringBuffer();
                linesOfFile.append(in.readLine()).append("\n");
                linesOfFile.append(in.readLine());
                byte[] bytes = file.getBytes();
                stream.write(bytes);
                return "Первые две строки из файла:\n" + linesOfFile;
            } catch (Exception e) {
                return "Вам не удалось загрузить файл" + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить потому что файл пустой.";
        }
    }


}

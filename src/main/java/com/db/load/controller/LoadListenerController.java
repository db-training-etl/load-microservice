package com.db.load.controller;

import com.db.load.service.FileLoaderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("load")
public class LoadListenerController {

    FileLoaderService fileLoaderService;

    @PostMapping("")
    public void load(@RequestParam String fileRoute) {
        fileLoaderService.load(fileRoute);
    }

}
package com.db.load.controller.counterparty;

import com.db.load.entity.Counterparty;
import com.db.load.service.FileLoaderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/counterparties/loadFromDirectory")
public class LoadCounterparty {

    FileLoaderService fileLoaderService;

    @PostMapping("")
    public void load(@RequestParam String fileRoute) throws IOException {
        fileLoaderService.load(fileRoute, Counterparty.class);
    }

}

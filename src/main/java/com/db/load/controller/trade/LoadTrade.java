package com.db.load.controller.trade;

import com.db.load.entity.Trade;
import com.db.load.service.FileLoaderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/trades/load")
public class LoadTrade {

    FileLoaderService fileLoaderService;

    @PostMapping("")
    public void load(@RequestParam String fileRoute) throws IOException {
        fileLoaderService.load(fileRoute, Trade.class);
    }

}

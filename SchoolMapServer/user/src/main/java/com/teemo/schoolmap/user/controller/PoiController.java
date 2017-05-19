package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.user.dto.Poi;
import com.teemo.schoolmap.user.service.IPoiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Teemo on 2017.05.18.
 */
@RestController
@RequestMapping("api/poi")
public class PoiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPoiService poiService;

    @GetMapping
    public ResponseEntity<List> getPois(){
        return new ResponseEntity<>(poiService.getPoi(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addPoi(@RequestBody Poi poi){
        return new ResponseEntity(poiService.addPou(poi) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

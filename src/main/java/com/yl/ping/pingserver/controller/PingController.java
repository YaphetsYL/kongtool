package com.yl.ping.pingserver.controller;

import com.yl.ping.pingserver.pojo.Ping;
import com.yl.ping.pingserver.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/6 - 05 - 06 - 20:39
 */

@RestController
@Log4j2
public class PingController {

    private static final String INVALID = "Invalid ip or domain";

    @ApiOperation("Ping ip or domain")
    @PostMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@Valid @RequestBody Ping server) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = server.getHost();
        JSONObject json = new JSONObject();
        //ip
        if (ValidateUtil.validateHost(host)) {
            InetAddress geek;
            try {
                geek = InetAddress.getByName(host);
                log.info("Sending Ping Request to " + host);
                if (geek.isReachable(5000)) {
                    log.info("Ping Host " + host + " successfully");
                    json.put("result", "Ping Host " + host + " successfully");
                    return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.OK);
                } else {
                    log.info("Unable to reach " + host);
                    json.put("result", "Unable to reach " + host);
                    return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                json.put("error", e.getMessage());
                return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
            }
        }

        log.error(INVALID);
        json.put("error", INVALID);
        return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
    }


}

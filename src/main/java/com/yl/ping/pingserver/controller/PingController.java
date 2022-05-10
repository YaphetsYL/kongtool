package com.yl.ping.pingserver.controller;

import com.yl.ping.pingserver.pojo.Ping;
import com.yl.ping.pingserver.util.ValidateUtil;
import lombok.extern.log4j.Log4j2;
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

    @PostMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@Valid @RequestBody Ping server) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = server.getHost();
        //ip
        if (ValidateUtil.validateHost(host)) {
            InetAddress geek;
            try {
                geek = InetAddress.getByName(host);
                log.info("Sending Ping Request to " + host);
                if (geek.isReachable(5000)) {
                    log.info("Host " + host + " is reachable");
                    return new ResponseEntity<>("{\"result\": \"Ping Success\"}", httpHeaders, HttpStatus.OK);
                } else {
                    log.info("Sorry ! We can't reach to this host " + host);
                    return new ResponseEntity<>("{\"result\": \"Ping Fail; Unreachable\"}", httpHeaders, HttpStatus.BAD_REQUEST);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>("{\"result\": \"Ping Fail; Unexpected error\"}", httpHeaders, HttpStatus.BAD_REQUEST);
            }
        }

        log.info("Invalid ip or domain");
        return new ResponseEntity<>("{\"result\": \"Invalid ip or domain\"}", httpHeaders, HttpStatus.BAD_REQUEST);
    }


}

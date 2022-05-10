package com.example.ping.pingserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/6 - 05 - 06 - 20:39
 */

@RestController
@Log4j2
public class PingController {

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingServer(@RequestHeader("IP") String ipAddress) {
        if (validate(ipAddress)) {
            InetAddress geek;
            try {
                geek = InetAddress.getByName(ipAddress);
                log.info("Sending Ping Request to " + ipAddress);
                if (geek.isReachable(5000)) {
                    log.info("Host " + ipAddress + " is reachable");
                    return new ResponseEntity<>("success",HttpStatus.OK);
                } else {
                    log.info("Sorry ! We can't reach to this host " + ipAddress);
                }
            } catch (IOException e) {
                log.error(e.getMessage());

            }
        }
        log.info("Invalid ip");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

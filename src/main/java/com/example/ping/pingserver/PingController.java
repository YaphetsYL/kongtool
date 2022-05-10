package com.example.ping.pingserver;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    private InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
    private DomainValidator domainValidator = DomainValidator.getInstance();

    @PostMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@RequestBody Ping server) {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String host = server.getHost();
        //ip
        if (inetAddressValidator.isValid(host) || domainValidator.isValid(host)) {
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
        return new ResponseEntity<>("{\"result\": \"Ping Fail; Invalid ip or domain\"}", httpHeaders, HttpStatus.BAD_REQUEST);
    }



    @PostMapping(value = "/telnet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> telnetServer(@RequestBody Ping ping) {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = ping.getHost();
        //ip
        if (inetAddressValidator.isValid(host) || domainValidator.isValid(host)) {
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
        return new ResponseEntity<>("{\"result\": \"Ping Fail; Invalid ip or domain\"}", httpHeaders, HttpStatus.BAD_REQUEST);
    }
}

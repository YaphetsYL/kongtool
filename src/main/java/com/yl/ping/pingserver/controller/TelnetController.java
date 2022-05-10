package com.yl.ping.pingserver.controller;

import com.yl.ping.pingserver.pojo.Telnet;
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
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/10 - 05 - 10 - 20:11
 */

@RestController
@Log4j2
public class TelnetController {

    private static final String ON_PORT = " on Port ";

    @PostMapping(value = "/telnet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@Valid @RequestBody Telnet telnet) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = telnet.getHost();
        int port = telnet.getPort();
        //ip
        if (ValidateUtil.validateHost(host)) {

            boolean isConnected;
            try (Socket telnetSocket = new Socket()) {
                log.info("Start Telnet server " + host + ON_PORT + port);
                telnetSocket.connect(new InetSocketAddress(host, port), 5000);
                isConnected = telnetSocket.isConnected();
            } catch (IOException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>("{\"result\": \"Telnet Fail\"}", httpHeaders, HttpStatus.BAD_REQUEST);
            }
            if (isConnected) {
                log.info("Host " + host + ON_PORT + port + " is reachable");
                return new ResponseEntity<>("{\"result\": \"Telnet Success\"}", httpHeaders, HttpStatus.OK);
            }
            log.info("Host " + host + ON_PORT + port + " is unreachable");
            return new ResponseEntity<>("{\"result\": \"Telnet Fail; Unexpected Error\"}", httpHeaders, HttpStatus.BAD_REQUEST);

        } else {
            log.info("Invalid ip or domain");
            return new ResponseEntity<>("{\"result\": \"Invalid ip or domain\"}", httpHeaders, HttpStatus.BAD_REQUEST);
        }


    }
}

package com.yl.ping.pingserver.controller;

import com.yl.ping.pingserver.pojo.Telnet;
import com.yl.ping.pingserver.util.ValidateUtil;
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
    private static final String RESULT = "result";

    @PostMapping(value = "/telnet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@Valid @RequestBody Telnet telnet) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = telnet.getHost();
        int port = telnet.getPort();
        JSONObject json = new JSONObject();
        //ip
        if (ValidateUtil.validateHost(host)) {

            boolean isConnected;
            try (Socket telnetSocket = new Socket()) {
                log.info("Start Telnet server " + host + ON_PORT + port);
                telnetSocket.connect(new InetSocketAddress(host, port), 5000);
                isConnected = telnetSocket.isConnected();
            } catch (IOException e) {
                log.error(e.getMessage());
                json.put(RESULT, e.getMessage());
                return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
            }
            if (isConnected) {
                log.info("Telnet Host " + host + ON_PORT + port + " successfully");
                json.put(RESULT, "Telnet Host " + host + ON_PORT + port + " successfully");
                return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.OK);
            }
            log.info("Host " + host + ON_PORT + port + " is unreachable");
            return new ResponseEntity<>("{\"result\": \"Telnet Fail; Unexpected Error\"}", httpHeaders, HttpStatus.BAD_REQUEST);

        } else {
            log.error("Invalid ip or domain");
            return new ResponseEntity<>("{\"error\": \"Invalid ip or domain\"}", httpHeaders, HttpStatus.BAD_REQUEST);
        }


    }
}

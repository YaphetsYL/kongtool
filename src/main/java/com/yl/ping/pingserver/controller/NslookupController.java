package com.yl.ping.pingserver.controller;

import com.yl.ping.pingserver.pojo.DNSRecord;
import com.yl.ping.pingserver.pojo.Ping;
import com.yl.ping.pingserver.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xbill.DNS.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lei
 * @version 1.0
 * @date 11/5/22 - 05 - 11 - 10:27 am
 */

@Log4j2
@RestController
public class NslookupController {

    private static final String ERROR = "error";
    private static final String INVALID = "Invalid domain";
    private static final String NSLOOKUP_HOST = "Nslookup Host ";

    @ApiOperation("Query the DNS")
    @PostMapping(value = "/nslookup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pingServer(@Valid @RequestBody Ping server) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String host = server.getHost();
        JSONObject json = new JSONObject();
        //ip
        if (ValidateUtil.isDomain(host)) {

            try {

                Lookup lookup = new Lookup(host, Type.A);
                lookup.run();
                if (lookup.getResult() != Lookup.SUCCESSFUL) {
                    log.error("Error: " + lookup.getErrorString());
                    json.put(ERROR, NSLOOKUP_HOST + host + " fail");
                    return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
                }
                //get resolver
                ExtendedResolver extendedResolver = (ExtendedResolver) Lookup.getDefaultResolver();
                SimpleResolver simpleResolver = (SimpleResolver) extendedResolver.getResolver(0);
                json.put("default resolver", simpleResolver.getAddress());

                Record[] records = lookup.getAnswers();
                ArrayList<DNSRecord> stringList = new ArrayList<>();
                for (Record rec : records) {
                    stringList.add(new DNSRecord(((ARecord) rec).getAddress().getHostName(), ((ARecord) rec).getAddress().getHostAddress()));
                }
                JSONArray jsonArray = new JSONArray(stringList);

                json.put("result", jsonArray);
                log.info(NSLOOKUP_HOST + host + " successfully");
                json.put("msg", NSLOOKUP_HOST + host + " successfully");
                return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.OK);
            } catch (IOException e) {
                log.error(e.getMessage());
                json.put(ERROR, e.getMessage());
                return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
            }
        }
        log.error(INVALID);
        json.put(ERROR, INVALID);
        return new ResponseEntity<>(json.toString(), httpHeaders, HttpStatus.BAD_REQUEST);
    }
}
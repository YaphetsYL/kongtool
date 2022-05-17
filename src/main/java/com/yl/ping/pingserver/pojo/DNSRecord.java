package com.yl.ping.pingserver.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Lei
 * @version 1.0
 * @date 11/5/22 - 05 - 11 - 12:28 pm
 */

@Getter
@Setter
@ApiModel
public class DNSRecord {

    private String domain;
    private String ip;

    public DNSRecord(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
    }
}
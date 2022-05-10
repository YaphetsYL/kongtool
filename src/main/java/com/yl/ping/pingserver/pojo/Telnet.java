package com.yl.ping.pingserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/10 - 05 - 10 - 21:27
 */

@Getter
@Setter
@ApiModel
public class Telnet {

    @NotNull
    @ApiModelProperty("host ip or domain")
    private String host;

    @NotNull
    private int port;

    public Telnet() {
    }

    public Telnet(String host, int port) {
        this.host = host;
        this.port = port;
    }
}

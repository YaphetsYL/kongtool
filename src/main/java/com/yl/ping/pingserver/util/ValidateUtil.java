package com.yl.ping.pingserver.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * @author Lei
 * @version 1.0
 * @date 2022/5/10 - 05 - 10 - 20:22
 */

@UtilityClass
public class ValidateUtil {
    private final InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
    private final DomainValidator domainValidator = DomainValidator.getInstance();

    public boolean validateHost(String host) {

        if (host == null || host.isEmpty() || host.trim().isEmpty()) {
            return false;
        }
        return inetAddressValidator.isValid(host) || domainValidator.isValid(host);
    }

    public boolean isDomain(String host) {

        if (host == null || host.isEmpty() || host.trim().isEmpty()) {
            return false;
        }
        return domainValidator.isValid(host);
    }

}

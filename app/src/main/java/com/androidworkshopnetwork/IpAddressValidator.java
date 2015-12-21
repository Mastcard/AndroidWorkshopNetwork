package com.androidworkshopnetwork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrien on 18/12/15.
 */
public class IpAddressValidator {

    /** The pattern. */
    private Pattern pattern;

    /** The Constant IPADDRESS_PATTERN. */
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    /**
     * Instantiates a new IpAddressValidator.
     */
    public IpAddressValidator(){
        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }

    /**
     * Validates the ip address.
     * @param ip
     * @return true valid ip address, false otherwise
     */
    public boolean validate(final String ip) {
        /* The matcher. */
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

}

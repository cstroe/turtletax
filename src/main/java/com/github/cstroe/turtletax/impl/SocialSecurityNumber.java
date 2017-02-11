package com.github.cstroe.turtletax.impl;

import lombok.Data;

@Data
public class SocialSecurityNumber {
    String ssn;

    public static SocialSecurityNumber of(String ssn) {
        SocialSecurityNumber newSsn = new SocialSecurityNumber();
        newSsn.setSsn(ssn);
        return newSsn;
    }
}

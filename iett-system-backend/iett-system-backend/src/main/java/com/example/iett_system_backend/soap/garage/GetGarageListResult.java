package com.example.iett_system_backend.soap.garage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetGarageListResult", namespace = "http://tempuri.org/", propOrder = {
        "any"
})
public class GetGarageListResult {

    @XmlElement(namespace = "http://tempuri.org/")
    protected String any;

    public String getAny() {
        return any;
    }

    public void setAny(String value) {
        this.any = value;
    }
}

package com.example.iett_system_backend.soap.bus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getLocationResult"
})
@XmlRootElement(name = "GetLocationResponse", namespace = "http://tempuri.org/")
public class GetLocationResponse {

    @XmlElement(name = "GetLocationResult", namespace = "http://tempuri.org/")
    protected GetLocationResult getLocationResult;

    public GetLocationResult getGetLocationResult() {
        return getLocationResult;
    }

    public void setGetLocationResult(GetLocationResult value) {
        this.getLocationResult = value;
    }
}

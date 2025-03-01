package com.example.iett_system_backend.soap.garage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getGarageListResult"
})
@XmlRootElement(name = "GetGarageListResponse", namespace = "http://tempuri.org/")
public class GetGarageListResponse {

    @XmlElement(name = "GetGarageListResult", namespace = "http://tempuri.org/")
    protected GetGarageListResult getGarageListResult;

    public GetGarageListResult getGetGarageListResult() {
        return getGarageListResult;
    }

    public void setGetGarageListResult(GetGarageListResult value) {
        this.getGarageListResult = value;
    }
}

package com.keruis.cilent.dao.pojos;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author
 */
@Alias("bs_460")
public class Bs460 implements Serializable {

    private String id;

    private Integer mcc;

    private Integer mnc;

    private Integer ac;

    private Long ci;

    private Integer ver;

    private Integer radius;

    private String address;

    private BigDecimal lat;

    private BigDecimal lng;

    private Integer ref;

    private String roads;

    private BigDecimal lata;

    private BigDecimal lnga;

    private BigDecimal latb;

    private BigDecimal lngb;

    private BigDecimal latg;

    private BigDecimal lngg;

    private Integer rid;

    private Long rids;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public Integer getMnc() {
        return mnc;
    }

    public void setMnc(Integer mnc) {
        this.mnc = mnc;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Long getCi() {
        return ci;
    }

    public void setCi(Long ci) {
        this.ci = ci;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public String getRoads() {
        return roads;
    }

    public void setRoads(String roads) {
        this.roads = roads;
    }

    public BigDecimal getLata() {
        return lata;
    }

    public void setLata(BigDecimal lata) {
        this.lata = lata;
    }

    public BigDecimal getLnga() {
        return lnga;
    }

    public void setLnga(BigDecimal lnga) {
        this.lnga = lnga;
    }

    public BigDecimal getLatb() {
        return latb;
    }

    public void setLatb(BigDecimal latb) {
        this.latb = latb;
    }

    public BigDecimal getLngb() {
        return lngb;
    }

    public void setLngb(BigDecimal lngb) {
        this.lngb = lngb;
    }

    public BigDecimal getLatg() {
        return latg;
    }

    public void setLatg(BigDecimal latg) {
        this.latg = latg;
    }

    public BigDecimal getLngg() {
        return lngg;
    }

    public void setLngg(BigDecimal lngg) {
        this.lngg = lngg;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Long getRids() {
        return rids;
    }

    public void setRids(Long rids) {
        this.rids = rids;
    }


    @Override
    public String toString() {
        return "Bs460{" +
                "id='" + id + '\'' +
                ", mcc=" + mcc +
                ", mnc=" + mnc +
                ", ac=" + ac +
                ", ci=" + ci +
                ", ver=" + ver +
                ", radius=" + radius +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", ref=" + ref +
                ", roads='" + roads + '\'' +
                ", lata=" + lata +
                ", lnga=" + lnga +
                ", latb=" + latb +
                ", lngb=" + lngb +
                ", latg=" + latg +
                ", lngg=" + lngg +
                ", rid=" + rid +
                ", rids=" + rids +
                '}';
    }
}
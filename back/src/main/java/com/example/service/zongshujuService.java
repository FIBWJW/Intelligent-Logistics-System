package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.VehicleMapper;
import com.example.mapper.zongshuju;
import com.example.pojo.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Service
public class zongshujuService {

    @Autowired
    private zongshuju zongshujumapper;

    public List<HashMap<String, BigDecimal>> getptfenlei() {
        List<HashMap<String, BigDecimal>> stringStringHashMap = zongshujumapper.countEmployees();
        return stringStringHashMap;
    }

    public List<HashMap<String, String>> getptfenlei1() {
        List<HashMap<String, String>> stringStringHashMap = zongshujumapper.countEmployees1();
        return stringStringHashMap;
    }

    public List<HashMap<String, String>> getptfenlei2() {
        List<HashMap<String, String>> stringStringHashMap = zongshujumapper.countEmployees2();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, String>> getptfenlei3() {
        List<HashMap<String, String>> stringStringHashMap = zongshujumapper.countEmployees3();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, String>> getptfenlei4() {
        List<HashMap<String, String>> stringStringHashMap = zongshujumapper.countEmployees4();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, String>> getptfenlei5() {
        List<HashMap<String, String>> stringStringHashMap = zongshujumapper.countEmployees5();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei6() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countEmployees6();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei7() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countEmployees7();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei8() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countEmployees8();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }
    public List<HashMap<String, BigDecimal >> getptfenlei9() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countEmployees9();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei10() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countEmployees10();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei11() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countOrderTime();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<String, BigDecimal >> getptfenlei12() {
        List<HashMap<String, BigDecimal >> stringStringHashMap = zongshujumapper.countSuperEmp();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }

    public List<HashMap<BigInteger, BigInteger >> getptfenlei13() {
        List<HashMap<BigInteger, BigInteger >> stringStringHashMap = zongshujumapper.countOrderPred();
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }
}

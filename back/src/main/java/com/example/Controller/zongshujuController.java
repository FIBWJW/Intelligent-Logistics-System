package com.example.Controller;


import com.example.Result;
import com.example.mapper.zhibiao01;
import com.example.pojo.LoginDTO;
import com.example.service.zongshujuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/sugar")
public class zongshujuController {
    @Autowired
    zongshujuService zongshujuService;

    @Autowired
    com.example.mapper.zongshuju zongshuju;

    @GetMapping("/data/zongshuju/pt")
    public List<HashMap<String, BigDecimal>> login1() {
        List<HashMap<String, BigDecimal>> getptfenlei = zongshujuService.getptfenlei();
        System.out.println(getptfenlei);

        return getptfenlei;
    }


    @GetMapping("/data/zongshuju/pt1")
    public List<HashMap<String, String>> login2() {
        List<HashMap<String, String>> getptfenlei = zongshujuService.getptfenlei1();
        System.out.println(getptfenlei);

        return getptfenlei;
    }


    @GetMapping("/data/zongshuju/pt2")
    public List<HashMap<String, String>> login3() {
        List<HashMap<String, String>> getptfenlei = zongshujuService.getptfenlei2();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt3")
    public List<HashMap<String, String>> login4() {
        List<HashMap<String, String>> getptfenlei = zongshujuService.getptfenlei3();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt4")
    public List<HashMap<String, String>> login5() {
        List<HashMap<String, String>> getptfenlei = zongshujuService.getptfenlei4();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt5")
    public List<HashMap<String, String>> login6() {
        List<HashMap<String, String>> getptfenlei = zongshujuService.getptfenlei5();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt6")
    public List<HashMap<String, BigDecimal>> login7() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei6();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt7")
    public List<HashMap<String, BigDecimal>> login8() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei7();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt8")
    public List<HashMap<String, BigDecimal>> login9() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei8();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt9")
    public List<HashMap<String, BigDecimal>> login10() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei9();
        System.out.println(getptfenlei);

        return getptfenlei;
    }


    @GetMapping("/data/zongshuju/pt10")
    public List<HashMap<String, BigDecimal>> login11() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei10();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt11")
    public List<HashMap<String, BigDecimal>> login12() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei11();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt12")
    public List<HashMap<String, BigDecimal>> login13() {
        List<HashMap<String, BigDecimal >> getptfenlei = zongshujuService.getptfenlei12();
        System.out.println(getptfenlei);

        return getptfenlei;
    }

    @GetMapping("/data/zongshuju/pt13")
    public List<HashMap<BigInteger, BigInteger>> login14() {
        List<HashMap<BigInteger, BigInteger >> getptfenlei = zongshujuService.getptfenlei13();
        System.out.println(getptfenlei);

        return getptfenlei;
    }



}

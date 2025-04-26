package com.example.Controller;

import com.example.bean.*;
import com.example.mapper.OrderMapper;
import com.example.mapper.zhibiao01;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sugar")
public class SugarController {
    @Autowired
    StatsService statsService;


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    zhibiao01 zhibiao01;

    @RequestMapping("/name")
    public String getname(int age){
        String name = statsService.selectName(18);
        return name;
    }


    // 用户管理
//    @PostMapping("/user")
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        userService.createUser(user);
//        return ResponseEntity.ok("User created successfully");
//    }
    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> saveUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User saved successfully");
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User saved successfully");
    }
    @GetMapping("/user/list")
    public ResponseEntity<?> Userlist() {
        return ResponseEntity.ok( userService.selctListuser());
    }

    @GetMapping("/user/list/name")
    public ResponseEntity<?> Userlistone(@RequestParam  String name) {
        System.out.println("xxxxx:"+name);
        return ResponseEntity.ok( userService.selctListuserone(name));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    //可视化接口
    @GetMapping("/zhibiao01")
    public ResponseEntity<?> getzhibiao01(){
        return ResponseEntity.ok(zhibiao01.selectName());
//        return zhibiao01.selectName();
    }

    @GetMapping("/zhibiao02")
    public ResponseEntity<?> getzhibiao02(){
        return ResponseEntity.ok(zhibiao01.selectName01());
//        return zhibiao01.selectName();
    }

    @GetMapping("/zhibiao03")
    public ResponseEntity<?> getzhibiao03(){
        return ResponseEntity.ok(zhibiao01.selectName02());
//        return zhibiao01.selectName();
    }

    @GetMapping("/zhibiao04")
    public ResponseEntity<?> getzhibiao04(){
        return ResponseEntity.ok(zhibiao01.selectName03());
//        return zhibiao01.selectName();
    }

    // 订单管理
//    @PostMapping("/order")
//    public ResponseEntity<?> createOrder(@RequestBody Orders orders) {
//        orderService.createOrder(orders);
//        return ResponseEntity.ok("Order created successfully");
//    }

//    @GetMapping("/order/{id}")
//    public ResponseEntity<?> getOrder(@PathVariable Long id) {
//        return ResponseEntity.ok(orderService.getOrder(id));
//    }

    // 物流管理
//    @GetMapping("/logistics/{orderId}")
//    public ResponseEntity<?> getLogistics(@PathVariable Long orderId) {
//        return ResponseEntity.ok(logisticsService.getOrder(orderId));
//    }

    // 员工管理
    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        return ResponseEntity.ok("Employee created successfully");
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping("/employee/list")
    public ResponseEntity<?> Employeelist() {
        return ResponseEntity.ok( employeeService.selctListemploy());
    }

    @GetMapping("/employee/list/name")
    public ResponseEntity<?> Employeelistname(@RequestParam String name) {
        return ResponseEntity.ok( employeeService.selctListemployone(name));
    }

    @PutMapping("/employee/update")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/employee/add")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveUser(employee);
        return ResponseEntity.ok("User saved successfully");
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok("User saved successfully");
    }

    // 添加车辆
    @PostMapping("/vehicle/add")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok("Vehicle added successfully");
    }

    // 查询所有车辆
    @GetMapping("/vehicle/list")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }


    @GetMapping("/vehicle/list/name")
    public ResponseEntity<Vehicle> getAllVehiclesone(@RequestParam String name) {
        return ResponseEntity.ok(vehicleService.getAllVehiclesone(name));
    }
    // 根据 ID 查询单个车辆
    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/vehicle/update")
    public ResponseEntity<?> updatevehicle(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/vehicle/delete/{id}")
    public ResponseEntity<?> deletevehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.ok("User updated successfully");
    }

    // 添加订单
    @PostMapping("/orders/add")
    public ResponseEntity<?> addOrder(@RequestBody Orders order) {
        orderService.addOrder(order);
        return ResponseEntity.ok("Order added successfully");
    }

    // 获取所有订单
    @GetMapping("/orders/list")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/list/name")
    public ResponseEntity<Orders> getAllOrders(@RequestParam String name) {
        return ResponseEntity.ok(orderService.getOrdersone(name));
    }



    // 根据 ID 获取订单
    @GetMapping("/orders/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("ddd");
    }

    @PutMapping("/orders/update")
    public ResponseEntity<?> updateorders(@RequestBody Orders orders) {
        orderService.updateorders(orders);
        return ResponseEntity.ok("User updated successfully");
    }


    // 添加物流记录
    @PostMapping("/logistics/add")
    public ResponseEntity<?> addLogistics(@RequestBody Logistics logistics) {
        Long orderId = logistics.getOrderId();
        String orderNumber = orderService.getOrderById(orderId).getOrderNumber();
        logistics.setLogisticsNumber("WL"+orderNumber);
        logisticsService.addLogistics(logistics);
        return ResponseEntity.ok("Logistics added successfully");
    }

    // 获取所有物流记录
    @GetMapping("/logistics/list")
    public ResponseEntity<List<LogisticsVo>> getAllLogistics() {
        return ResponseEntity.ok(logisticsService.getAllLogistics());
    }

    @GetMapping("/logistics/list/name")
    public ResponseEntity<LogisticsVo> getAllLogisticsone(@RequestParam String name) {
        return ResponseEntity.ok(logisticsService.getAllLogisticsone(name));
    }

    // 根据 ID 获取物流记录
    @GetMapping("/logistics/{id}")
    public ResponseEntity<Logistics> getLogisticsById(@PathVariable Long id) {
        return ResponseEntity.ok(logisticsService.getLogisticsById(id));
    }

    @DeleteMapping("/logistics/delete/name")
    public ResponseEntity<?> deletelogtistics(@RequestParam String name){
        logisticsService.deleteone(name);
        return ResponseEntity.ok("kkk");
    }

    @GetMapping("/dashboard/index")
    public ResponseEntity<?> getInfo(@RequestParam String name){
        logisticsService.deleteone(name);
        return ResponseEntity.ok("kkk");
    }
}

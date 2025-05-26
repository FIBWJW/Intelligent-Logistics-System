package com.example.Controller;

import com.example.Result;
import com.example.pojo.*;
import com.example.mapper.zhibiao01;
import com.example.service.*;
import com.example.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private LogUtil logUtil;

    @RequestMapping("/name")
    public String getname(int age){
        String name = statsService.selectName(18);
        return name;
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        logUtil.recordLog("更新用户信息: " + user.getUsername(), "PUT /api/sugar/user/update");
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        logUtil.recordLog("删除用户: ID=" + id, "DELETE /api/sugar/user/delete/{id}");
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.saveUser(user);
        logUtil.recordLog("新增用户: " + user.getUsername(), "POST /api/sugar/user/add");
        return ResponseEntity.ok("User saved successfully");
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> listUsers() {
        logUtil.recordLog("查询用户列表", "GET /api/sugar/user/list");
        return ResponseEntity.ok(userService.selectListuser());
    }

    @GetMapping("/user/list/name")
    public ResponseEntity<?> listUsersByName(@RequestParam String name) {
        logUtil.recordLog("根据用户名查询用户列表: " + name, "GET /api/sugar/user/list/name");
        return ResponseEntity.ok(userService.selectListuser(name));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        logUtil.recordLog("查询用户详情: ID=" + id, "GET /api/sugar/user/{id}");
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/zhibiao01")
    public ResponseEntity<?> getzhibiao01(){
        logUtil.recordLog("查询指标 01", "GET /api/sugar/zhibiao01");
        return ResponseEntity.ok(zhibiao01.selectName());
    }

    @GetMapping("/zhibiao02")
    public ResponseEntity<?> getzhibiao02(){
        logUtil.recordLog("查询指标 02", "GET /api/sugar/zhibiao02");
        return ResponseEntity.ok(zhibiao01.selectName01());
    }

    @GetMapping("/zhibiao03")
    public ResponseEntity<?> getzhibiao03(){
        logUtil.recordLog("查询指标 03", "GET /api/sugar/zhibiao03");
        return ResponseEntity.ok(zhibiao01.selectName02());
    }

    @GetMapping("/zhibiao04")
    public ResponseEntity<?> getzhibiao04(){
        logUtil.recordLog("查询指标 04", "GET /api/sugar/zhibiao04");
        return ResponseEntity.ok(zhibiao01.selectName03());
    }

    @GetMapping("/zhibiao05")
    public ResponseEntity<?> getzhibiao05(){
        logUtil.recordLog("查询指标 05", "GET /api/sugar/zhibiao05");
        return ResponseEntity.ok(zhibiao01.selectName04());
    }
    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        logUtil.recordLog("新增员工: " + employee.getName(), "POST /api/sugar/employee");
        return ResponseEntity.ok("Employee created successfully");
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        logUtil.recordLog("查询员工详情: ID=" + id, "GET /api/sugar/employee/{id}");
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping("/employee/list")
    public ResponseEntity<?> listEmployees() {
        logUtil.recordLog("查询员工列表", "GET /api/sugar/employee/list");
        return ResponseEntity.ok(employeeService.selctListemploy());
    }

    @GetMapping("/employee/list/name")
    public ResponseEntity<?> listEmployeesByName(@RequestParam String name) {
        logUtil.recordLog("根据姓名查询员工列表: " + name, "GET /api/sugar/employee/list/name");
        return ResponseEntity.ok(employeeService.selctListemployone(name));
    }

    @PutMapping("/employee/update")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        logUtil.recordLog("更新员工信息: " + employee.getName(), "PUT /api/sugar/employee/update");
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/employee/add")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveUser(employee);
        logUtil.recordLog("新增员工: " + employee.getName(), "POST /api/sugar/employee/add");
        return ResponseEntity.ok("User saved successfully");
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        logUtil.recordLog("删除员工: ID=" + id, "DELETE /api/sugar/employee/delete/{id}");
        return ResponseEntity.ok("User saved successfully");
    }

    @PostMapping("/vehicle/add")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        logUtil.recordLog("新增车辆: " + vehicle.getLicensePlate(), "POST /api/sugar/vehicle/add");
        return ResponseEntity.ok("Vehicle added successfully");
    }

    @GetMapping("/vehicle/list")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        logUtil.recordLog("查询所有车辆", "GET /api/sugar/vehicle/list");
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/vehicle/list/name")
    public ResponseEntity<Vehicle> getVehicleByName(@RequestParam String name) {
        logUtil.recordLog("根据车辆名称查询车辆: " + name, "GET /api/sugar/vehicle/list/name");
        return ResponseEntity.ok(vehicleService.getAllVehiclesone(name));
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        logUtil.recordLog("查询车辆详情: ID=" + id, "GET /api/sugar/vehicle/{id}");
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/vehicle/update")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
        logUtil.recordLog("更新车辆信息: " + vehicle.getLicensePlate(), "PUT /api/sugar/vehicle/update");
        return ResponseEntity.ok("Vehicle updated successfully");
    }

    @DeleteMapping("/vehicle/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        logUtil.recordLog("删除车辆: ID=" + id, "DELETE /api/sugar/vehicle/delete/{id}");
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

    @PostMapping("/orders/add")
    public ResponseEntity<?> addOrder(@RequestBody Orders order) {
        orderService.addOrder(order);
        logUtil.recordLog("新增订单: " + order.getOrderNumber(), "POST /api/sugar/orders/add");
        return ResponseEntity.ok("Order added successfully");
    }

    @GetMapping("/orders/list")
    public ResponseEntity<List<Orders>> getAllOrders() {
        logUtil.recordLog("查询所有订单", "GET /api/sugar/orders/list");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/list/name")
    public ResponseEntity<Orders> getOrderByName(@RequestParam String name) {
        logUtil.recordLog("根据订单号查询订单: " + name, "GET /api/sugar/orders/list/name");
        return ResponseEntity.ok((Orders) orderService.getOrdersone(name));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        logUtil.recordLog("查询订单详情: ID=" + id, "GET /api/sugar/orders/{id}");
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        logUtil.recordLog("删除订单: ID=" + id, "DELETE /api/sugar/orders/delete/{id}");
        return ResponseEntity.ok("Order deleted successfully");
    }

    @PutMapping("/orders/update")
    public ResponseEntity<?> updateOrders(@RequestBody Orders orders) {
        orderService.updateorders(orders);
        logUtil.recordLog("更新订单信息: " + orders.getOrderNumber(), "PUT /api/sugar/orders/update");
        return ResponseEntity.ok("Order updated successfully");
    }

    @PostMapping("/logistics/add")
    public ResponseEntity<?> addLogistics(@RequestBody Logistics logistics) {
        Long orderId = logistics.getOrderId();
        String orderNumber = orderService.getOrderById(orderId).getOrderNumber();
        logistics.setLogisticsNumber("WL"+orderNumber);
        logisticsService.addLogistics(logistics);
        logUtil.recordLog("新增物流记录: " + logistics.getLogisticsNumber(), "POST /api/sugar/logistics/add");
        return ResponseEntity.ok("Logistics added successfully");
    }

    @GetMapping("/logistics/list")
    public ResponseEntity<List<LogisticsVo>> getAllLogistics() {
        logUtil.recordLog("查询所有物流记录", "GET /api/sugar/logistics/list");
        return ResponseEntity.ok(logisticsService.getAllLogistics());
    }

    @GetMapping("/logistics/list/name")
    public ResponseEntity<LogisticsVo> getLogisticsByName(@RequestParam String name) {
        logUtil.recordLog("根据物流编号查询物流记录: " + name, "GET /api/sugar/logistics/list/name");
        return ResponseEntity.ok(logisticsService.getAllLogisticsone(name));
    }

    @GetMapping("/logistics/{id}")
    public ResponseEntity<Logistics> getLogisticsById(@PathVariable Long id) {
        logUtil.recordLog("查询物流记录详情: ID=" + id, "GET /api/sugar/logistics/{id}");
        return ResponseEntity.ok(logisticsService.getLogisticsById(id));
    }

    @DeleteMapping("/logistics/delete/name")
    public ResponseEntity<?> deleteLogistics(@RequestParam String name){
        logisticsService.deleteone(name);
        logUtil.recordLog("删除物流记录: 物流编号=" + name, "DELETE /api/sugar/logistics/delete/name");
        return ResponseEntity.ok("Logistics deleted successfully");
    }
}


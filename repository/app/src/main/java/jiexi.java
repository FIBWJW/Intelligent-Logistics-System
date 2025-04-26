//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class jiexi {
//    public static void main(String[] args) {
//        String record = "SourceRecord{sourcePartition={server=mysql_binlog_source}, sourceOffset={file=binlog.000002, pos=83080}} ConnectRecord{topic='mysql_binlog_source.app.credit', kafkaPartition=null, key=Struct{id=10}, keySchema=Schema{mysql_binlog_source.app.credit.Key:STRUCT}, value=Struct{after=Struct{id=10,contract_produce_time=2025-01-03T06:09:04Z,credit_amount=189000.00,status=3,contract_id=10,credit_facility_id=10,create_time=2024-12-24T06:09:04Z,update_time=2024-12-24T06:09:12Z},source=Struct{version=1.4.1.Final,connector=mysql,name=mysql_binlog_source,ts_ms=0,snapshot=last,db=app,table=credit,server_id=0,file=binlog.000002,pos=83080,row=0},op=c,ts_ms=1735477980533}, valueSchema=Schema{mysql_binlog_source.app.credit.Envelope:STRUCT}, timestamp=null, headers=ConnectHeaders(headers=)}";
//
//        // 正则表达式提取 "after" 部分
//        String regex = "after=Struct\\{([^}]*)\\}";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(record);
//
//        if (matcher.find()) {
//            String after = matcher.group(1);  // 提取 "after" 部分的内容
//            System.out.println("After field content: " + after);
//            // 使用逗号分割每个键值对
//            String[] keyValuePairs = after.split(",");
//
//            // 创建一个 Map 来存储这些数据
//            Map<String, String> dataMap = new HashMap<>();
//
//            // 遍历所有的键值对
//            for (String pair : keyValuePairs) {
//                // 使用等号分割每个键和值
//                String[] keyValue = pair.split("=");
//                if (keyValue.length == 2) {
//                    // 将键值对存入 Map 中
//                    dataMap.put(keyValue[0].trim(), keyValue[1].trim());
//                }
//            }
//
//            System.out.println(dataMap);
//        } else {
//            System.out.println("No match found");
//        }
//    }
//    }
//

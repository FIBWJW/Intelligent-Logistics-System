package FullsinkHdfs.aTest0105;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.hadoop.security.UserGroupInformation;
import readsinkTest.CustomerDeserialization;
import readsinkTest.FlinkHdfsSinkFunction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * employee 员工表
 */
public class sourceMysqlToHdfs07 {
    public static void main(String[] args) throws Exception {

        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");

        ugi.doAs(new java.security.PrivilegedExceptionAction<Void>() {
            @Override
            public Void run() throws Exception {

                StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
                env.setParallelism(1);

                Properties properties = new Properties();
                properties.setProperty("scan.startop.mode","initial");

//                properties.setProperty("snapshot.mode", "initial"); // 更改为适合的模式
//                properties.setProperty("scan.startup.mode", "initial"); // 控制扫描模式


                //构建输入流
                DebeziumSourceFunction<String> build = MySQLSource.<String>builder()
                        .hostname("localhost")
                        .port(3306)
                        .username("root")
                        .password("1234")
                        .databaseList("lyzTestzhu")
                        .tableList("lyzTestzhu.employee_order_count")
                        .serverTimeZone("Asia/Shanghai")
                        .debeziumProperties(properties)
                        .deserializer(new CustomerDeserialization())
                        .build();

                //source算子 ：作用-》读取
                //输入流
                DataStreamSource<String> stringDataStreamSource = env.addSource(build);
                stringDataStreamSource.print();

                //map   转换算子    map：提取数据
                SingleOutputStreamOperator<String> noMatchFound = stringDataStreamSource.map(new MapFunction<String, String>() {
                    @Override
                    public String map(String s) throws Exception {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        System.out.println(jsonObject);
                        JSONObject after = jsonObject.getJSONObject("after");
                        Collection<String> values = Arrays.asList(Arrays.toString(after.values().toArray()));

                        System.out.println("values:"+values);
                        return values.toString();
                    }
                });

                //输出数据路径
                String hdfsOutputPath = "hdfs://bigdata:9000/datashuju/ads_employee_order_count";
//                noMatchFound.print("kkk");
                //输出流
                noMatchFound.addSink(new FlinkHdfsSinkFunction(hdfsOutputPath));

                env.execute();
                return null;
            }
        });

    }
}

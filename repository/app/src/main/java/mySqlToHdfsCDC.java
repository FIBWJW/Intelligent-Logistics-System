import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import com.alibaba.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.kafka.connect.source.SourceRecord;
import readsinkTest.CustomerDeserialization;
import readsinkTest.FlinkHdfsSinkFunction;

import java.util.regex.*;
import java.util.*;
import java.util.Properties;

/**
 * TODO 1.使用FlinkCDC读取Binglong日志，解析数据变化，实时增量插入到HDFS
 *      source：credit
 *      sink:  datadb/credit
 */
public class mySqlToHdfsCDC {
    public static void main(String[] args) throws Exception {

        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");

        ugi.doAs(new java.security.PrivilegedExceptionAction<Void>() {
            @Override
            public Void run() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        Properties properties = new Properties();
        properties.setProperty("scan.startop.mode","initial");

        DebeziumSourceFunction<String> build = MySQLSource.<String>builder()
                .hostname("localhost")
                .port(3306)
                .username("root")
                .password("1234")
                .databaseList("app")
                .tableList("app.credit")
                .debeziumProperties(properties)
                .deserializer(new CustomerDeserialization())
                .build();

        DataStreamSource<String> stringDataStreamSource = env.addSource(build);

        SingleOutputStreamOperator<String> noMatchFound = stringDataStreamSource.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
//                System.out.println("实际数据："+s);
                // 正则表达式提取 "after" 部分
                JSONObject jsonObject = JSONObject.parseObject(s);
                System.out.println(jsonObject);
                JSONObject after = jsonObject.getJSONObject("after");
                Collection<String> values = Arrays.asList(Arrays.toString(after.values().toArray()));

                System.out.println("values:"+values);
                return values.toString();
            }
        });

        String hdfsOutputPath = "hdfs://master:9000/datadb/credit";
//                noMatchFound.print("kkk");
        noMatchFound.addSink(new FlinkHdfsSinkFunction(hdfsOutputPath));

        env.execute();
                return null;
            }
        });

    }
}

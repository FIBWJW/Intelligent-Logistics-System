package readsinkTest;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Serializable;


/**
 *
 * TODO： 1.自定义sink到HdfsFunction
 *
 */
public class FlinkHdfsSinkFunction implements SinkFunction<String>, Serializable{
    private final String hdfsOutputPath;

    public FlinkHdfsSinkFunction(String hdfsOutputPath) {
        this.hdfsOutputPath = hdfsOutputPath;
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        // 配置 HDFS 连接
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://bigdata:9000"); // 设置 HDFS 地址
        configuration.set("dfs.replication", "1");
        FileSystem fileSystem = FileSystem.get(configuration);

        // 创建输出路径
        Path outputPath = new Path(hdfsOutputPath);
        if (!fileSystem.exists(outputPath.getParent())) {
            fileSystem.mkdirs(outputPath.getParent());
        }

//        // 打开输出流，将数据写入 HDFS
//        try (BufferedWriter writer = new BufferedWriter(
//                new OutputStreamWriter(fileSystem.create(outputPath, true)))) {
//            writer.write(value);
//            writer.newLine();
//        }

        // 打开输出流，以追加模式写入数据
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(fileSystem.append(outputPath)))) {
            writer.write(value);
            writer.newLine();
        } catch (Exception e) {
            // 如果文件不存在（第一次写入），切换到创建模式
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(fileSystem.create(outputPath)))) {
                writer.write(value);
                writer.newLine();
            }
        }
    }
}

//package readsinkTest;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//
//import java.io.BufferedWriter;
//import java.io.OutputStreamWriter;
//import java.io.Serializable;
//
//public class sinklyz implements SinkFunction<String>, Serializable {
//
//
//
//        private final String hdfsOutputPath;
//        private transient FileSystem fileSystem; // 使用 transient 来避免序列化问题
//
//        public sinklyz(String hdfsOutputPath) {
//            this.hdfsOutputPath = hdfsOutputPath;
//        }
//
//        // 初始化 HDFS 配置和 FileSystem，只需要在构造时执行一次
//        private void initializeFileSystem() throws Exception {
//            if (fileSystem == null) {
//                Configuration configuration = new Configuration();
//                configuration.set("fs.defaultFS", "hdfs://master:9000");
//                fileSystem = FileSystem.get(configuration);
//            }
//        }
//
//        @Override
//        public void invoke(String value, Context context) throws Exception {
//            // 初始化 HDFS 连接
//            initializeFileSystem();
//
//            Path outputPath = new Path(hdfsOutputPath);
//            // 创建输出路径的父目录（如果不存在）
//            if (!fileSystem.exists(outputPath.getParent())) {
//                fileSystem.mkdirs(outputPath.getParent());
//            }
//
//            // 检查文件是否存在
//            boolean fileExists = fileSystem.exists(outputPath);
//
//            // 如果文件存在，使用追加模式；否则，使用创建模式
//            try (BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(fileExists ? fileSystem.append(outputPath) : fileSystem.create(outputPath)))) {
//                writer.write(value);
//                writer.newLine();
//            }
//        }
//    }
//
//

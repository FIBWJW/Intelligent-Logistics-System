//import org.apache.flink.api.java.io.TextInputFormat;
//import org.apache.flink.api.java.io.TextOutputFormat;
//import org.apache.flink.core.fs.Path;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
//import org.apache.hadoop.security.UserGroupInformation;
//
//public class readHdfs {
//    public static void main(String[] args) throws Exception {
//        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
//
//        ugi.doAs(new java.security.PrivilegedExceptionAction<Void>() {
//            @Override
//            public Void run() throws Exception {
//                // 设置执行环境
//                StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//                env.setParallelism(1);
//
//                // 指定HDFS上的文件路径
//                String hdfsFilePath = "hdfs://master:9000/data/business_partner";
////                String hdfsFilePath = "hdfs://master:9000/data1/output2/";
//                String hdfsOutputPath = "hdfs://master:9000/data1/output3/";
//
//                // 创建TextInputFormat来读取文本文件
//                TextInputFormat textInputFormat = new TextInputFormat(new Path(hdfsFilePath));
//
//                // 使用readFile方法读取HDFS上的文件，设置为持续处理模式
//                DataStreamSource<String> textStream = env.readFile(
//                        textInputFormat, hdfsFilePath, FileProcessingMode.PROCESS_CONTINUOUSLY, 10000L);
//
//                // 打印读取到的数据
//                textStream.print();
//
//
//                // 创建一个TextOutputFormat来写入数据到HDFS
//                TextOutputFormat<String> textOutputFormat = new TextOutputFormat<>(new Path(hdfsOutputPath));
//
//                // 将读取到的文本数据写入到HDFS输出目录
////                textStream.writeUsingOutputFormat(textOutputFormat);
//// 添加自定义 HDFS Sink
//                textStream.addSink(new sinkFunction(hdfsOutputPath));
//
//                // 执行任务
//                env.execute("Flink Read HDFS File");
//
//                return null;
//            }
//        });
//
//
//
//
//
//    }
//}

package exercise38.SparkDataframe;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkDriver {

    public static void main(String args[]) {

        String inputPath = args[0], outputPath = args[1];

        SparkSession ss = SparkSession.builder()
                .appName("Exercise 38 - SparkDataframe")
                .master("local")
                .getOrCreate();

        Dataset<Row> readings = ss
                .read()
                .format("csv")
                .option("header", false)
                .option("inferSchema", true)
                .load(inputPath);

        Dataset<Row> result = readings
                .filter("_c2 > 50")
                .groupBy("_c0")
                .count()
                .withColumnRenamed("_c0", "sensorId")
                .filter("count >= 2");

        result.write().format("csv").save(outputPath);

        ss.close();

    }

}

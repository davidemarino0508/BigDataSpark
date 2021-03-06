package exercise32.SparkSQL;

import exercise32.Readings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkDriver {

    public static void main(String args[]) {

        String inputPath = args[0];

        SparkSession ss = SparkSession.builder()
                .appName("Exercise 32 - SparkSQL")
                .master("local")
                .getOrCreate();

        Dataset<Row> readings = ss
                .read()
                .format("csv")
                .option("header", false)
                .option("inferSchema", true)
                .load(inputPath);

        Dataset<Readings> result = readings
                .as(Encoders.bean(Readings.class));

        result.createOrReplaceTempView("readings");

        Dataset<Double> maxValue = ss
                .sql("SELECT MAX(_c2) FROM readings")
                .as(Encoders.DOUBLE());

        System.out.println(maxValue.first());

        ss.stop();

    }

}

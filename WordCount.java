import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        private final Text word = new Text();
        private final LongWritable one = new LongWritable(1);

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // Convert the input line to string
            String line = value.toString();

            // Split the line by commas (assuming it's a CSV file) to get the attributes
            String[] attributes = line.split(",");

            // Check if the line has enough attributes and the "DrugName" attribute is available
            if (attributes.length >= 4) { 
                // Get the value of the "DrugName" attribute
                String drugName = attributes[3].trim();

                // Split the review into words using whitespace as delimiter
                String[] words = drugName.split("\\s+");

                // Emit each word with a count of 1
                for (String w : words) {
                    word.set(w);
                    context.write(word, one);
                }
            }
        }
    }

    public static class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private final LongWritable result = new LongWritable();

        @Override
        public void reduce(Text key, Iterable<LongWritable> values, Context context)
                throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        // Start measuring the processing time
        long startTime = System.currentTimeMillis();

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Word Count");

        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);

        // End measuring the processing time
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        System.out.println("Processing Time: " + processingTime + " milliseconds");

        System.exit(success ? 0 : 1);
    }
}

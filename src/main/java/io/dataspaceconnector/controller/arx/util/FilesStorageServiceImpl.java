package io.dataspaceconnector.controller.arx.util;

import io.dataspaceconnector.arx.DataType;
import io.dataspaceconnector.arx.aggregates.HierarchyBuilderDate;
import io.dataspaceconnector.arx.aggregates.HierarchyBuilderDate.Format;
import io.dataspaceconnector.arx.aggregates.HierarchyBuilderGroupingBased;
import io.dataspaceconnector.arx.aggregates.HierarchyBuilderIntervalBased;
import io.dataspaceconnector.arx.aggregates.HierarchyBuilderOrderBased;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("uploads");

    private MultipartFile _file;

    public FilesStorageServiceImpl() {
        this._file = null;
    }

    private static String[] getExampleDateData_ori() {
        String stringFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);

        String[] result = new String[100];
        for (int i = 0; i < result.length; i++) {

            Calendar date = GregorianCalendar.getInstance();
            date.add(Calendar.HOUR, i);

            result[i] = format.format(date.getTime());
        }
        return result;
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            _file = file;
            if (!Files.exists(Path.of(_file.getOriginalFilename()))) {
                Files.copy(file.getInputStream(), this.root.resolve(_file.getOriginalFilename()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public List<String> getAllItems() {
        List<String> lineList = new ArrayList<>();
        try {
            var file = load(_file.getOriginalFilename()).getFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lineList.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    @Override
    public List<String> getLines(int count) {
        List<String> lineList = new ArrayList<>();
        int lineNumber = 0;
        try {
            var file = load(_file.getOriginalFilename()).getFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber < count)
                        lineList.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    @Override
    public String[][] orderBased() {

        // Create the builder
        HierarchyBuilderOrderBased<String> builder = HierarchyBuilderOrderBased
                .create(DataType.STRING, false);

        // Define grouping fan outs
        // Alternatively
        builder.setAggregateFunction(DataType.STRING.createAggregate().createSetFunction());
        builder.getLevel(0)
                .addGroup(1);

        System.out.println("---------------------");
        System.out.println("ORDER-BASED HIERARCHY");
        System.out.println("---------------------");
        System.out.println("");
        System.out.println("SPECIFICATION");

        // Print specification
        for (HierarchyBuilderGroupingBased.Level<String> level : builder.getLevels()) {
            System.out.println(level);
        }


        // Print info about resulting groups
        System.out.println("Resulting levels: " + Arrays.toString(builder.prepare(getExampleDataOne())));

        System.out.println("");
        System.out.println("RESULT");

        // Print resulting hierarchy
        return builder.build().getHierarchy();
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public String[][] dates() {
        var granularities = HierarchyBuilderDate.Granularity.values();
        DataType<Date> type =   DataType.createDate("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH);
        var format = new Format();
        var timeZone = TimeZone.getDefault();
        Date bottomCodingBound = null;
        Date topCodingBound = null;

        var builder = HierarchyBuilderDate.create(type, HierarchyBuilderDate.Granularity.DAY_MONTH_YEAR);

        var hierarchy = builder.build(getExampleDateData()).getHierarchy();

        System.out.println("---------------------");
        System.out.println("RESULT");
        System.out.println(Arrays.deepToString(hierarchy));
        return hierarchy;
    }

    @SneakyThrows
    private String[] getExampleDateData() {
        Set<String> set = new HashSet<>();
        try {
            File csvData = new File("/home/sali/Downloads/MOCK_DATA.csv");
            FileReader fileReader = new FileReader(csvData);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(','));
            for (CSVRecord csvRecord : csvParser) {
                var birth_date = csvRecord.get("birth_date");
                set.add(birth_date);
            }
        } catch (IOException exception) {

            System.out.println(exception.getMessage());
        }
        var result = new String[set.size()];
        int counter = 0;
        for (var item : set) {
            result[counter] = item;
            counter++;
        }
        return result;
    }

    /**
     * Returns example data.
     */
    private String[] getExampleData() {

        Set<Integer> set = new HashSet<>();
        try {
            File csvData = new File("/home/sali/Downloads/arx/data/adult.csv");
            FileReader fileReader = new FileReader(csvData);
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(','));
            for (CSVRecord csvRecord : csvParser) {
                var age = Integer.valueOf(csvRecord.get("age"));
                set.add(age);
            }
        } catch (IOException exception) {

            System.out.println(exception.getMessage());
        }
        var result = new String[set.size()];
        int counter = 0;
        for (Integer item : set) {
            result[counter] = item.toString();
            counter++;
        }
        return result;
    }


    private String[] getExampleDataOne() {

        Set<String> set = new HashSet<>();
        try {
            File csvData = new File("/home/sali/Downloads/arx/data/adult.csv");
            FileReader fileReader = new FileReader(csvData);
            CSVParser csvParser = CSVParser.parse(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(','));
            for (CSVRecord csvRecord : csvParser) {
                var age = csvRecord.get("sex");
                set.add(age);
            }
        } catch (IOException exception) {

            System.out.println(exception.getMessage());
        }
        var result = new String[set.size()];
        int counter = 0;
        for (String item : set) {
            result[counter] = item;
            counter++;
        }
        return result;
    }


    /**
     * Exemplifies the use of the interval-based builder.
     */
    @Override
    public String[][] intervalBased() {
        //HierarchyBuilderOrderBased

        // Create the builder
        HierarchyBuilderIntervalBased<Long> builder = HierarchyBuilderIntervalBased.create(
                DataType.INTEGER,
                new HierarchyBuilderIntervalBased.Range<>(0L, 0L, 0L),
                new HierarchyBuilderIntervalBased.Range<>(80L, 80L, 91L));

        // Define base intervals
        builder.setAggregateFunction(DataType.INTEGER.createAggregate()
                .createIntervalFunction(true, false));
        builder.addInterval(0L, 5L);


        // Define grouping fan outs
        builder.getLevel(0).addGroup(2);
        builder.getLevel(1).addGroup(2);
        builder.getLevel(2).addGroup(2);
        builder.getLevel(3).addGroup(2);
        System.out.println("------------------------");
        System.out.println("INTERVAL-BASED HIERARCHY");
        System.out.println("------------------------");
        System.out.println();
        System.out.println("SPECIFICATION");
        // Print specification
        for (HierarchyBuilderIntervalBased.Interval<Long> interval : builder.getIntervals()) {
            System.out.println(interval);
        }

        //  Print specification
        for (HierarchyBuilderGroupingBased.Level<Long> level : builder.getLevels()) {
            System.out.println(level);
        }

        // Print info about resulting levels
        System.out.println("Resulting levels: " + Arrays.toString(builder.prepare(getExampleData())));

        System.out.println();
        System.out.println("RESULT");

        return builder.build().getHierarchy();
    }

    private void printArray(String[][] hierarchy) {
        for (String[] strList : hierarchy) {
            for (String str : strList) {
                System.out.print(str);
            }
            System.out.println();
        }
    }
}

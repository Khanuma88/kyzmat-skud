package kg.kyzmat.skud;

import kg.kyzmat.skud.imports.ExcelEventImporter;
import kg.kyzmat.skud.model.DailyAttendance;
import kg.kyzmat.skud.report.ReportPrinter;
import kg.kyzmat.skud.service.AttendanceService;
import kg.kyzmat.skud.service.StatisticsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class SkudApplication implements CommandLineRunner {

    private final ExcelEventImporter importer;
    private final AttendanceService attendanceService;
    private final StatisticsService statisticsService;
    private final ReportPrinter reportPrinter;

    public SkudApplication(ExcelEventImporter importer,
                           AttendanceService attendanceService,
                           StatisticsService statisticsService,
                           ReportPrinter reportPrinter) {
        this.importer = importer;
        this.attendanceService = attendanceService;
        this.statisticsService = statisticsService;
        this.reportPrinter = reportPrinter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SkudApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File file = new File("C:/Users/Ханума/Downloads/acs_agregator_public_acs_event.xlsx");
        importer.importFromFile(file);

        List<DailyAttendance> days = attendanceService.getDailyAttendance();
        statisticsService.calculateViolations(days);

        int workingDays = statisticsService.countWorkingDays(2026, 8);
        reportPrinter.print(days, workingDays);
    }
}
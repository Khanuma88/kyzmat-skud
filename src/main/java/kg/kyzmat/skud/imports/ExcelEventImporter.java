package kg.kyzmat.skud.imports;

import kg.kyzmat.skud.entity.AcsEventEntity;
import kg.kyzmat.skud.repository.AcsEventRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelEventImporter {

    private final AcsEventRepository repository;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSSSSS]");

    public ExcelEventImporter(AcsEventRepository repository) {
        this.repository = repository;
    }

    public void importFromFile(File file) throws Exception {
        List<AcsEventEntity> events = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet("Result 1");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idNumberCell = row.getCell(1);
                Cell personIdCell = row.getCell(2);
                Cell timeCell = row.getCell(3);
                Cell ipCell = row.getCell(4);
                Cell nameCell = row.getCell(5);
                Cell deviceCell = row.getCell(6);

                if (idNumberCell == null || timeCell == null) continue;

                AcsEventEntity event = new AcsEventEntity();
                event.setIdNumber(getStringValue(idNumberCell));
                event.setPersonId(getStringValue(personIdCell));
                event.setInsertTime(parseDateTime(timeCell));
                event.setIpAddress(getStringValue(ipCell));
                event.setName(getStringValue(nameCell));
                event.setDeviceName(getStringValue(deviceCell));

                events.add(event);
            }
        }

        repository.deleteAll();
        repository.saveAll(events);
        System.out.println("Импортировано записей: " + events.size());
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private LocalDateTime parseDateTime(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getLocalDateTimeCellValue();
        }
        String raw = cell.getStringCellValue().trim();
        return LocalDateTime.parse(raw, TIME_FORMAT);
    }
}
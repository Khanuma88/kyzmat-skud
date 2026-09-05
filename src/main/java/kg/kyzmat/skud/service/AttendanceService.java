package kg.kyzmat.skud.service;

import kg.kyzmat.skud.entity.AcsEventEntity;
import kg.kyzmat.skud.model.DailyAttendance;
import kg.kyzmat.skud.repository.AcsEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AcsEventRepository repository;

    public AttendanceService(AcsEventRepository repository) {
        this.repository = repository;
    }

    public List<DailyAttendance> getDailyAttendance() {
        List<AcsEventEntity> events = repository.findAllSkudEvents();

        Map<String, List<AcsEventEntity>> grouped = events.stream()
                .collect(Collectors.groupingBy(e ->
                        e.getPersonId() + "_" + e.getInsertTime().toLocalDate()));

        List<DailyAttendance> result = new ArrayList<>();

        for (Map.Entry<String, List<AcsEventEntity>> entry : grouped.entrySet()) {
            List<AcsEventEntity> dayEvents = entry.getValue();

            AcsEventEntity first = dayEvents.get(0);
            LocalDate date = first.getInsertTime().toLocalDate();

            LocalTime earliest = dayEvents.stream()
                    .map(e -> e.getInsertTime().toLocalTime())
                    .min(LocalTime::compareTo)
                    .orElse(null);

            LocalTime latest = dayEvents.stream()
                    .map(e -> e.getInsertTime().toLocalTime())
                    .max(LocalTime::compareTo)
                    .orElse(null);

            DailyAttendance daily = new DailyAttendance(
                    first.getPersonId(),
                    first.getName(),
                    date,
                    earliest,
                    latest
            );

            result.add(daily);
        }

        result.sort(Comparator
                .comparing(DailyAttendance::getDate)
                .thenComparing(DailyAttendance::getFullName));

        return result;
    }
}
package kg.kyzmat.skud.service;

import kg.kyzmat.skud.config.WorkScheduleConfig;
import kg.kyzmat.skud.model.DailyAttendance;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {

    private final WorkScheduleConfig schedule = new WorkScheduleConfig();

    public void calculateViolations(List<DailyAttendance> days) {
        for (DailyAttendance day : days) {
            if (day.getFirstCheckIn() != null &&
                    day.getFirstCheckIn().isAfter(schedule.getShiftStart())) {
                day.setLate(true);
                day.setLateMinutes(Duration.between(
                        schedule.getShiftStart(), day.getFirstCheckIn()).toMinutes());
            }

            if (day.getLastCheckOut() != null &&
                    day.getLastCheckOut().isBefore(schedule.getShiftEnd())) {
                day.setEarlyLeave(true);
                day.setEarlyLeaveMinutes(Duration.between(
                        day.getLastCheckOut(), schedule.getShiftEnd()).toMinutes());
            }

            if (day.getLastCheckOut() != null &&
                    day.getLastCheckOut().isAfter(schedule.getShiftEnd())) {
                day.setLateLeave(true);
                day.setLateLeaveMinutes(Duration.between(
                        schedule.getShiftEnd(), day.getLastCheckOut()).toMinutes());
            }
        }
    }

    public int countWorkingDays(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        int count = 0;
        while (date.getMonthValue() == month) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }
}
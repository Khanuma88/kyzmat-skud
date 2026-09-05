package kg.kyzmat.skud.report;

import kg.kyzmat.skud.model.DailyAttendance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportPrinter {

    public void print(List<DailyAttendance> days, int workingDaysInMonth) {
        Map<String, List<DailyAttendance>> byEmployee = days.stream()
                .collect(Collectors.groupingBy(DailyAttendance::getFullName));

        System.out.println();
        System.out.println("=== Отчёт по посещаемости ===");
        System.out.println("Рабочих дней в месяце: " + workingDaysInMonth);
        System.out.println();
        System.out.printf("%-35s %-10s %-10s %-10s %-10s%n",
                "Сотрудник", "Опоздания", "Ранние", "Поздние", "Без нарушений");

        for (Map.Entry<String, List<DailyAttendance>> entry : byEmployee.entrySet()) {
            List<DailyAttendance> employeeDays = entry.getValue();

            long lateCount = employeeDays.stream().filter(DailyAttendance::isLate).count();
            long earlyCount = employeeDays.stream().filter(DailyAttendance::isEarlyLeave).count();
            long lateLeaveCount = employeeDays.stream().filter(DailyAttendance::isLateLeave).count();
            long violationFreeCount = employeeDays.stream().filter(DailyAttendance::isViolationFree).count();

            System.out.printf("%-35s %-10d %-10d %-10d %-10d%n",
                    entry.getKey(), lateCount, earlyCount, lateLeaveCount, violationFreeCount);
        }

        System.out.println("Всего дней с записями: " + days.size());
    }
}
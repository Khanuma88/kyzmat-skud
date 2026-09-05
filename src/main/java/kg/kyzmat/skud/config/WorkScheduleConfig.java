package kg.kyzmat.skud.config;

import java.time.LocalTime;

public class WorkScheduleConfig {

    private LocalTime shiftStart = LocalTime.of(9, 0);
    private LocalTime shiftEnd = LocalTime.of(18, 0);

    public LocalTime getShiftStart() { return shiftStart; }
    public void setShiftStart(LocalTime shiftStart) { this.shiftStart = shiftStart; }

    public LocalTime getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(LocalTime shiftEnd) { this.shiftEnd = shiftEnd; }
}
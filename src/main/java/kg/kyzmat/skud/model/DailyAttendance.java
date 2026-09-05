package kg.kyzmat.skud.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DailyAttendance {

    private String employeeId;
    private String fullName;
    private LocalDate date;
    private LocalTime firstCheckIn;
    private LocalTime lastCheckOut;

    private boolean late;
    private long lateMinutes;

    private boolean earlyLeave;
    private long earlyLeaveMinutes;

    private boolean lateLeave;
    private long lateLeaveMinutes;

    public DailyAttendance() {
    }

    public DailyAttendance(String employeeId, String fullName, LocalDate date,
                           LocalTime firstCheckIn, LocalTime lastCheckOut) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.date = date;
        this.firstCheckIn = firstCheckIn;
        this.lastCheckOut = lastCheckOut;
    }

    public boolean isViolationFree() {
        return !late && !earlyLeave;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getFirstCheckIn() { return firstCheckIn; }
    public void setFirstCheckIn(LocalTime firstCheckIn) { this.firstCheckIn = firstCheckIn; }

    public LocalTime getLastCheckOut() { return lastCheckOut; }
    public void setLastCheckOut(LocalTime lastCheckOut) { this.lastCheckOut = lastCheckOut; }

    public boolean isLate() { return late; }
    public void setLate(boolean late) { this.late = late; }

    public long getLateMinutes() { return lateMinutes; }
    public void setLateMinutes(long lateMinutes) { this.lateMinutes = lateMinutes; }

    public boolean isEarlyLeave() { return earlyLeave; }
    public void setEarlyLeave(boolean earlyLeave) { this.earlyLeave = earlyLeave; }

    public long getEarlyLeaveMinutes() { return earlyLeaveMinutes; }
    public void setEarlyLeaveMinutes(long earlyLeaveMinutes) { this.earlyLeaveMinutes = earlyLeaveMinutes; }

    public boolean isLateLeave() { return lateLeave; }
    public void setLateLeave(boolean lateLeave) { this.lateLeave = lateLeave; }

    public long getLateLeaveMinutes() { return lateLeaveMinutes; }
    public void setLateLeaveMinutes(long lateLeaveMinutes) { this.lateLeaveMinutes = lateLeaveMinutes; }
}
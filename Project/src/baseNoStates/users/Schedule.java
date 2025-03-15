package baseNoStates.users;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Schedule class defines access times and dates for user groups.
public class Schedule {
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final List<DayOfWeek> validDays;
  private final LocalDate startDate;
  private final LocalDate endDate;

  public Schedule(LocalTime startTime, LocalTime endTime, List<DayOfWeek> validDays,
        LocalDate startDate, LocalDate endDate) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.validDays = validDays;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  // Checks if a schedule is valid using start and end schedules.
  public boolean isWithinSchedule(LocalDate currentDate, LocalTime currentTime) {
    return !currentDate.isBefore(this.startDate)
            && !currentDate.isAfter(this.endDate)
            && this.validDays.contains(currentDate.getDayOfWeek())
            && !currentTime.isBefore(this.startTime)
            && !currentTime.isAfter(this.endTime);
  }

  // Provides a string representation of the schedule.
  @Override
  public String toString() {
    return "[Time: " + this.startTime + "-" + this.endTime + ", Days: " + this.validDays
            + ", Date range: " + this.startDate + " to " + this.endDate + "]";
  }
}

/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses with a specific log file.
     */
    public LogAnalyzer()
    { 
        // Initialize counts arrays.
        hourCounts = new int[24];
        dayCounts = new int[28]; // Assuming 28 days in a month for simplicity.
        monthCounts = new int[12]; // One for each month.
        
        // Create the reader to obtain the data from the specified file.
        reader = new LogfileReader();
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            int day = entry.getDay() - 1;   // Assuming days are 1-28.
            int month = entry.getMonth() - 1; // Assuming months are 1-12.
            
            hourCounts[hour]++;
            dayCounts[day]++;
            monthCounts[month]++;
        }
    }

    /**
     * Returns the total number of accesses in the log file.
     */
    public int numberOfAccesses() {
        int totalAccesses = 0;
        while (reader.hasNext()) {
            reader.next();
            totalAccesses++;
        }
        return totalAccesses;
    }

    /**
     * Returns the hour with the most accesses.
     */
    public int busiestHour() {
        int maxHour = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > hourCounts[maxHour]) {
                maxHour = hour;
            }
        }
        return maxHour;
    }

    /**
     * Returns the hour with the fewest accesses.
     */
    public int quietestHour() {
        int minHour = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < hourCounts[minHour]) {
                minHour = hour;
            }
        }
        return minHour;
    }

    /**
     * Returns the starting hour of the busiest two-hour period.
     */
    public int busiestTwoHour() {
        int maxStartHour = 0;
        int maxCount = hourCounts[0] + hourCounts[1];
        for (int hour = 1; hour < hourCounts.length - 1; hour++) {
            int twoHourCount = hourCounts[hour] + hourCounts[hour + 1];
            if (twoHourCount > maxCount) {
                maxCount = twoHourCount;
                maxStartHour = hour;
            }
        }
        return maxStartHour;
    }

    /**
     * Returns the day with the fewest accesses.
     */
    public int quietestDay() {
        int minDay = 0;
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] < dayCounts[minDay]) {
                minDay = day;
            }
        }
        return minDay + 1; // Adjusting to match day numbering (1-28)
    }

    /**
     * Returns the day with the most accesses.
     */
    public int busiestDay() {
        int maxDay = 0;
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] > dayCounts[maxDay]) {
                maxDay = day;
            }
        }
        return maxDay + 1; // Adjusting to match day numbering (1-28)
    }

    /**
     * Returns the total accesses per month.
     */
    public int[] totalAccessesPerMonth() {
        return monthCounts;
    }

    /**
     * Returns the month with the fewest accesses.
     */
    public int quietestMonth() {
        int minMonth = 0;
        for (int month = 1; month < monthCounts.length; month++) {
            if (monthCounts[month] < monthCounts[minMonth]) {
                minMonth = month;
            }
        }
        return minMonth + 1; // Adjusting to match month numbering (1-12)
    }

    /**
     * Returns the month with the most accesses.
     */
    public int busiestMonth() {
        int maxMonth = 0;
        for (int month = 1; month < monthCounts.length; month++) {
            if (monthCounts[month] > monthCounts[maxMonth]) {
                maxMonth = month;
            }
        }
        return maxMonth + 1; // Adjusting to match month numbering (1-12)
    }

    /**
     * Returns the average number of accesses per month.
     */
    public double averageAccessesPerMonth() {
        int totalAccesses = 0;
        for (int monthCount : monthCounts) {
            totalAccesses += monthCount;
        }
        return (double) totalAccesses / monthCounts.length;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}

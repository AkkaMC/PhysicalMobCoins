package store.jseries.framework.utils.players;

public class TimerBuilder {

    public static String getFormatLongDays(String s, long temps) {
        long totalSecs = temps / 1000L;
        return String.format(s,
                totalSecs / 86400L, totalSecs % 86400L / 3600L,
                totalSecs % 3600L / 60L, totalSecs % 60L);
    }

    public static String getFormatLongHours(String s,long temps) {
        long totalSecs = temps / 1000L;
        return String.format(s, totalSecs / 3600L,
                totalSecs % 3600L / 60L, totalSecs % 60L);
    }

    public static String getFormatLongHoursSimple(String s, long temps) {
        long totalSecs = temps / 1000L;
        return String.format(s, totalSecs / 3600L,
                totalSecs % 3600L / 60L, totalSecs % 60L);
    }

    public static String getFormatLongMinutes(String s, long temps) {
        long totalSecs = temps / 1000L;
        return String.format(s,
                totalSecs % 3600L / 60L, totalSecs % 60L);
    }

    public static String getFormatLongSecondes(String s, long temps) {
        long totalSecs = temps / 1000L;
        return String.format(s, totalSecs % 60L);
    }

    public static String getStringTime(String s, long second) {
        if (second < 60)
            return (TimerBuilder.getFormatLongSecondes(s,second * 1000L));
        else if (second >= 60 && second < 3600)
            return (TimerBuilder.getFormatLongMinutes(s,second * 1000L));
        else if (second >= 3600 && second < 86400)
            return (TimerBuilder.getFormatLongHours(s,second * 1000L));
        else
            return (TimerBuilder.getFormatLongDays(s,second * 1000L));
    }
}
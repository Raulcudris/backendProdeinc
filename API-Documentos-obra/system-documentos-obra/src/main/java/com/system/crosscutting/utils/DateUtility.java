package com.system.crosscutting.utils;

import com.system.crosscutting.domain.constants.Constants;

import io.micrometer.core.instrument.util.TimeUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Log4j2
@UtilityClass
public class DateUtility {

    private static final Logger logger = LogManager.getLogger(TimeUtils.class);

    public static Date getActualDateTime() {
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.US_DATE_TIME_FORMAT_SSS, Locale.US);

            return dateTimeFormat.parse(dateTimeFormat.format(new Date()));
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        return new Date();
    }

    public static String completeWithHours(String date, String hours) {
        return date.length() < 11 ? date.concat(hours) : date;
    }

    public static Date parseUsDateTime(String date) {
        return parseUsDateTime(date, null);
    }

    public static Date parseUsDateTime(String date, String format) {
        if (Objects.nonNull(format) && !format.isEmpty()) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(date);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        } else {
            try {
                return new SimpleDateFormat(Constants.US_DATE_TIME_FORMAT_SSS, Locale.US).parse(date);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }

            try {
                return new SimpleDateFormat(Constants.US_DATE_TIME_FORMAT, Locale.US).parse(date);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }

            try {
                return new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_US, Locale.US).parse(date);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }

        return new Date();
    }

    /** Devuelve la hora local sin ningun ajueste (hora del servidor) */
    public EntyDate getDateTime() {

        EntyDate ex = new EntyDate();
        ex.date = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ex.time = Float.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.MM")));
        ex.dateTime = LocalDateTime.now();
        return ex;
    }
   
    /** Devolver fecha y hora ajustada a la hora local aplicando valor UTC (ejm: -5,  +1, -3 y otros mas) */
    public EntyDate getDateTimeUTC(int valueUTC) {

        EntyDate ex = new EntyDate();
        // Obtener la hora actual en UTC
        OffsetDateTime utcNow = OffsetDateTime.now(ZoneOffset.UTC);
    
        try {
            // Crear un ZoneOffset a partir del valor numérico UTC (ejemplo: -5, +1)
            ZoneOffset zoneOffset = ZoneOffset.ofHours(valueUTC);
    
            // Ajustar la hora UTC al valor UTC proporcionado
            OffsetDateTime localTime = utcNow.withOffsetSameInstant(zoneOffset);
    
            // Llenar el objeto EntyDate con los valores ajustados
            ex.date = localTime.toLocalDate();
            ex.dateTime = localTime.toLocalDateTime();
            ex.time = Float.valueOf(localTime.format(DateTimeFormatter.ofPattern("HH.mm")));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ex;        
    }

    public class EntyDate
    {
        public LocalDate date;
        public LocalDateTime dateTime;
        public Float time;
    }

    /**
     * Calcula y genera una descripción amigable del tiempo transcurrido entre dos fechas.
     *
     * @param startDate Fecha inicial
     * @param endDate   Fecha final
     * @return genera texto como "Hace 2 horas", "Hace un mes", etc.
     */
    public static String getPeriodDateComments(LocalDateTime startDate, LocalDateTime endDate) {
        String response = "Hace unos segundos";

        try {
            if (startDate.isAfter(endDate)) {
                logger.warn("⚠️ La fecha inicial es posterior a la final: start={}, end={}", startDate, endDate);
                return "Fecha inválida";
            }

            long minutes = ChronoUnit.MINUTES.between(startDate, endDate);
            long hours = ChronoUnit.HOURS.between(startDate, endDate);
            long days = ChronoUnit.DAYS.between(startDate, endDate);
            Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());

            // Casos rápidos por minutos y horas
            if (minutes <= 1) {
                response = "Hace unos segundos";
            } else if (minutes <= 49) {
                response = "Hace " + minutes + " minutos";
            } else if (minutes <= 60) {
                response = "Hace 1 hora";
            } else if (hours < 24) {
                response = "Hace " + hours + " horas";
            }
            // En días
            else if (days == 1) {
                response = "Hace un día";
            } else if (days <= 5) {
                response = "Hace menos de una semana";
            } else if (days <= 8) {
                response = "Hace una semana";
            } else if (days <= 15) {
                response = "Hace dos semanas";
            } else if (period.getMonths() == 0 && period.getYears() == 0) {
                response = "Hace " + days + " días";
            }
            // En meses
            else if (period.getYears() == 0) {
                String suffix = "";
                if (period.getDays() >= 12 && period.getDays() <= 17) {
                    suffix = " y medio";
                } else if (period.getDays() >= 18) {
                    suffix = " y " + period.getDays() + " días";
                }

                if (period.getMonths() == 1 && period.getDays() >= 24) {
                    response = "Hace ya casi dos meses";
                } else {
                    response = (period.getMonths() == 1 ? "Hace un mes" : "Hace " + period.getMonths() + " meses") + suffix;
                }
            }
            // En años
            else {
                String suffix = "";
                if (period.getMonths() >= 4 && period.getMonths() <= 7) {
                    suffix = " y medio";
                } else if (period.getMonths() >= 2) {
                    suffix = " y " + period.getMonths() + " meses";
                }

                response = (period.getYears() == 1 ? "Hace un año" : "Hace " + period.getYears() + " años") + suffix;
            }

        } catch (Exception e) {
            logger.error("❌ Error al calcular periodo entre fechas: {} - {}", startDate, endDate, e);
        }

        logger.info("📅 Resultado cálculo periodo: {}", response);
        return response;
    }
}

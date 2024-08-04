package com.example.applestore.util;
import com.example.applestore.model.enums.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModelAttributeUtil {

   private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static String formatPrice(BigDecimal price) {
        if (price == null) {
            return "N/A";
        }
        return decimalFormat.format(price);
    }

    public static void addEnumsToIphoneModel(ModelAndView model) {
        model.addObject("capacityRams", CapacityRam.values());
        model.addObject("colours", Colour.values());
        model.addObject("displays", Display.values());
        model.addObject("internalMemories", InternalMemory.values());
        model.addObject("batteries", Battery.values());
    }

    public static void addEnumsToMacBookModel(ModelAndView model) {
        model.addObject("capacityRams", CapacityRam.values());
        model.addObject("colours", Colour.values());
    }

}

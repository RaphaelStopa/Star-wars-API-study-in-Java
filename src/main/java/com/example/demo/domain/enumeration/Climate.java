package com.example.demo.domain.enumeration;

public enum Climate {
    //here the business rule is based on land and degrees celcius. Average temperatures from 15 to 25, hot
    // from 26 to 40 and very hot from 40 and above. 14 to 4 cold and below that very cold.
    MEDIAN,
    HOT,
    VERY_HOT,
    VERY_COLD,
    COLD
}

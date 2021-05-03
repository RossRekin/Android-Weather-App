package pu.fmi.rainytime.models;

import pu.fmi.rainytime.R;

public class IconPicker {
    private final int [] images = {R.drawable.thunderstorm,R.drawable.athmosphere,R.drawable.clear,R.drawable.clearnight,R.drawable.clouds,R.drawable.rain,R.drawable.snow};

    public int pickIcon(String weatherType,String time){
        switch (weatherType){
            case "Thunderstorm":
                return images[0];
            case "Drizzle":
                return images[5];
            case "Rain":
                return images[5];
            case "Snow":
                return images[6];
            case "Atmosphere":
                return images[1];
            case "Clear":
                String type= DayOrNightCheck(time);
                if (type.equals("day")){
                    return images[2];
                }else if (type.equals("night")){
                    return images[3];
                }
            case "Clouds":
                return images[4];
        }
        return 0;
    }

    private String DayOrNightCheck(String time){
        String[] tokens = time.split(" ");
        if(tokens.length>1) {
            String[] hours = tokens[1].split(":");
            int hour = Integer.parseInt(hours[0]);
            if(hour<=6 || hour>=20) {
                return "night";
            }
        }
        return "day";
    }
}

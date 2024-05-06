package com.example.chumbatelegramm.configuration;

public class UrlConstants {

    public static String GROUPS_URL = "https://tahvel.edu.ee/hois_back/schoolBoard/8/group/timetables";

    //Когда то давно когда воздух был чище а трава зеленее, ограничение по датам в ссылке работало :)
//    public String generateURL(Integer groupId){
//        return "https://tahvel.edu.ee/hois_back/schoolBoard/8/timetableByGroup?from="
//                + LocalDate.now()+"T00:00:00Z&studentGroups="+groupId+"&thru=" + LocalDate.now().plusDays(5)+"T00:00:00Z";
//    }
    public static String generateURL(Integer groupId) {
        return "https://tahvel.edu.ee/hois_back/schoolBoard/8/timetableByGroup?studentGroups=" + groupId;
    }

}

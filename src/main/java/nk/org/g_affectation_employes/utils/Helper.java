package nk.org.g_affectation_employes.utils;

public class Helper {
    public static String toCapitalize(String str){
        if(str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

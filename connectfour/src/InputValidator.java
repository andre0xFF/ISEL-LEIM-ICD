public class InputValidator {




    public static boolean isNationalityValid(String nationality){
        String regex = "[a-zA-Z]{3}";
        return nationality.matches(regex);
    }

    public static boolean isImageUrlValid(String url){
        String regex = "^(https?|ftp)://[\\w-]+";
        return  url.matches(regex);
    }

    public  static boolean isInputEmpty(String input){

        return input.equals("");
    }

    public static boolean isPasswordEmpty(char[] pass){
        return pass.length == 0;
    }
}

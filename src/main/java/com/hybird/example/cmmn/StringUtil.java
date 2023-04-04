package com.hybird.example.cmmn;

import java.util.Random;

public class StringUtil {
	/**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     */
    public static String numberGen(int len, int dupCd ) {
        
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
        
        for(int i=0;i<len;i++) {
        	 //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }
    /**
     * String null 처리
     */
	public static String nvl(String str, String defaultStr) {
		if(str == null || str == "" || str.length() == 0) {
			str = defaultStr;
		}
        return  str ;
	}
	
	//문자열이 숫자(정수, 실수)인지 아닌지 판별한다.
	public static boolean isNumber(String str) {
	    char tempCh;
	    int dotCount = 0;	//실수일 경우 .의 개수를 체크하는 변수
	    boolean result = true;

	    for (int i=0; i<str.length(); i++){
	      tempCh= str.charAt(i);	//입력받은 문자열을 문자단위로 검사
	      //아스키 코드 값이 48 ~ 57사이면 0과 9사이의 문자이다.
	      if ((int)tempCh < 48 || (int)tempCh > 57){
	        //만약 0~9사이의 문자가 아닌 tempCh가 .도 아니거나
	        //.의 개수가 이미 1개 이상이라면 그 문자열은 숫자가 아니다.
	        if(tempCh!='.' || dotCount > 0){
	          result = false;
	          break;
	        }else{
	          //.일 경우 .개수 증가
	          dotCount++;
	        }
	      }
	    }
	    return result;
	  }
	//String 공백 체크
	public static boolean isEmpty(String spaceCheck)
	{
	    for(int i = 0 ; i < spaceCheck.length() ; i++)
	    {
	        if(spaceCheck.charAt(i) == ' ')
	            return true;
	    }
	    return false;
	}
	
}

package com.swapnil.QueryHub.utils;

import java.time.Instant;
import java.time.LocalDateTime;

public class CursorUtils {

    public static boolean isValidCursor(String cursor){

        if(cursor == null || cursor.isEmpty()){
            return false;
        }

        try{
            Instant.parse(cursor);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static Instant parseCursor(String cursor){

        if(!isValidCursor(cursor))
            throw new IllegalArgumentException("Invalid cursor");
        return Instant.parse(cursor);
    }
}

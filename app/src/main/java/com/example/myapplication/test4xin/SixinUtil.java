package com.example.myapplication.test4xin;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SixinUtil {
    public static boolean suExecCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec("su_root");

        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        os.writeBytes(command +"\n");
        os.writeBytes("exit\n");
        os.flush();
        InputStream inputstream = process.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        String data = "";
        String s;
        while ((s = bufferedreader.readLine()) != null) {
            data += s + "\n";
            Log.d("cmd","recv:"+s);
        }

//        Log.d("cmd","redump:");
//        Log.d("cmd","recv:"+data);
        //finally
        {
            if (inputstream != null)
                inputstream.close();
            if (inputstreamreader != null)
                inputstreamreader.close();
        }
        return true;
    }
}

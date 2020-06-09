package com.stylefeng.guns.order.config.util;

import org.apache.commons.net.ftp.FTPClient;
import sun.net.ftp.FtpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FTPUtil {
    private String hostname = "localhost";
    private Integer port = 21;
    private String username = "";
    private String password = "";

    private FTPClient getClient() {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(hostname, port);
            ftpClient.login(username, password);

            return ftpClient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFileStrByAddress(String fileAddress) {
        FTPClient ftpClient = getClient();
        if (ftpClient == null) {
            return null;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                    ftpClient.retrieveFileStream(fileAddress)));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            }

            ftpClient.logout();

            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

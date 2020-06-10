package com.stylefeng.guns.order.config.util;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sun.net.ftp.FtpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {
    private String hostname;
    private Integer port;
    private String username;
    private String password;

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

    public String getFileStrFromResource(String filename) {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("seat.json");
        if (inputStream == null) {
            return null;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            }

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

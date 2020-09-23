package com.gps.greplog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.util.*;

public class LogTest{
	public static void main(String[] args){
		Connection con = null;
        PreparedStatement pstmt = null;   
        ResultSet rs = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://10.131.13.86:3306/test", "root", "mz1234");
            String fileName="C:\\Users\\keehy\\coway.2020-09-22.log";
			File file = new File(fileName); 
			if(file.exists()) { 
				BufferedReader inFile = new BufferedReader(new FileReader(file)); 
				String sLine = null;
				boolean start=false;
				StringBuffer lineBlock = new StringBuffer();

                String time=null;
                String apiName=null;
                String errStep=null;

                boolean isErrorBlock=false;
                String requestBody=null; 
                String empNo=null;
                int reqGubun=0;

                int totCnt=0;

				while( (sLine = inFile.readLine()) != null ){
                    totCnt=totCnt+1; 
					if(sLine.contains("=================================          START         =================================")){
						start=true;
                        time=sLine.substring(0,19);
					}
                    
                    if(start){
                        lineBlock.append(sLine);
                        lineBlock.append("\n");
                    }

                    if(sLine.contains("Request URI 	:")){
                        apiName=sLine.substring(sLine.indexOf("Request URI 	: "), sLine.length());                    
                    }


                    /* two line 0907~0910 */
                    

                    if(reqGubun==1){   
                        requestBody=sLine;   
                        int idx=sLine.indexOf("empNo");
                        if(idx>0){
                            empNo=sLine.substring(idx+8, idx+16);
                        }
                        reqGubun=0;
                    }

                    if(sLine.contains("Request Body 	:")){
                        requestBody=sLine.substring(sLine.indexOf("Request Body 	:"), sLine.length());   
                        int idx=sLine.indexOf("empNo");
                        if(idx>0){
                            empNo=sLine.substring(idx+8, idx+16);
                        }else{
                            reqGubun=reqGubun+1;
                        }
                    }

                    /* one line after 0910 
                    if(sLine.contains("Request Body 	:")){

                        requestBody=sLine.substring(sLine.indexOf("Request Body 	:"), sLine.length());   

                        int idx=sLine.indexOf("empNo");

                        if(idx>0){
                            empNo=sLine.substring(idx+8, idx+16);
                        }
                    }
                    */


                    if(sLine.contains("[Error Step]")){
                        errStep=sLine;                      
                    }

                    if(sLine.contains("<API SERVICE ERROR>")){
                        isErrorBlock=true;
                    }

					if(sLine.contains("=================================           END          =================================")){
                        if(isErrorBlock){
                            String query = " insert into fuel_api_log (time, api_name, key_param, result, log, emp_no)" + " values (?, ?, ?, ?, ?, ?)";
                            
                            pstmt = con.prepareStatement(query);
                            pstmt.setString (1, time);
                            pstmt.setString (2, apiName);
                            pstmt.setString (3, requestBody);
                            pstmt.setString (4, errStep);
                            pstmt.setString (5, lineBlock.toString());
                            pstmt.setString (6, empNo);
                            pstmt.execute();
                        }
                        lineBlock.setLength(0);
                        start=false;
                        errStep=null;
                        isErrorBlock=false;
                        requestBody=null;
                        empNo=null;
					}
				}
			}
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close(); 
                }
                
                if(pstmt != null) {
                    pstmt.close(); 
                }
            
                if(con != null) {
                    con.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
	}
}
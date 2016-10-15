package com.stockAccounting.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.stockAccounting.Master.stockMaster;

public class KeywordDriverScript 
{
	public static String strRes=null;
	stockMaster sm=new stockMaster();
	@Test
	public void key() throws IOException
	{
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date=new Date();
		String d=format.format(date);
		System.out.println(d);
		
		String d1=d.replace("/", "");
		String d2=d1.replace(" ", "");
		String d3=d2.replace(":", "");
		System.out.println(d3);
		
		
		String xlpath="E:\\Newfolder\\stockAccounting\\src\\com\\stockAccounting\\Testdata\\keyword.xlsx";
		String xlout="E:\\Newfolder\\stockAccounting\\src\\com\\stockAccounting\\Results\\OJTKEYRES"+d3+".xlsx";
		FileInputStream fi=new FileInputStream(xlpath);
		XSSFWorkbook wb=new XSSFWorkbook(fi);
		XSSFSheet TCSht=wb.getSheet("TestCase");
		XSSFSheet TSSht=wb.getSheet("TestSteps");
		
		int intTC=TCSht.getLastRowNum();
		int intTS=TSSht.getLastRowNum();
		
		
		for (int i = 1; i <= intTC; i++)
		{
			TCSht.getRow(i).createCell(3);
			String strExe=TCSht.getRow(i).getCell(2).getStringCellValue();
			if (strExe.equalsIgnoreCase("Y"))
			{
				String TcId=TCSht.getRow(i).getCell(0).getStringCellValue();
				
				for (int j = 1; j <= intTS; j++) 
				{
					String TsTcId=TSSht.getRow(j).getCell(0).getStringCellValue();
					if (TcId.equalsIgnoreCase(TsTcId))
					{
						String strKey=TSSht.getRow(j).getCell(3).getStringCellValue();
						switch (strKey) 
						{
						case "sLanch":
							strRes=sm.stockAcc_Launch("http://webapp.qedgetech.com");
							break;
						case "sLogin":	
							strRes=sm.stockAcc_Login("admin", "master");
							break;
						case "sLogout":	
							strRes=sm.stockAcc_Logout();
							sm.stockAcc_Close();
							break;
						case "sSupplier":
							strRes=sm.stockAcc_SupplierCreation("QEdge12345", "Ameerpet", "Hyderabad", "INDIA", "SivaPrasad", "9874561234", "Siva@gmail.com", "9874561235", "HI, It provides Training");
							break;
						case "sCat":
							strRes=sm.stockAcc_stockcatCreation("QEDGE98765");
							break;
						default:
							System.out.println("Select a Proper Keyword");
							break;
						}
						TSSht.getRow(j).createCell(4).setCellValue(strRes);
						String TCRes=TCSht.getRow(i).getCell(3).getStringCellValue();
						if (!TCRes.equalsIgnoreCase("Fail"))
						{
							TCSht.getRow(i).getCell(3).setCellValue(strRes);
						}
					}
				}
			}
			else
			{
				TCSht.getRow(i).createCell(3).setCellValue("BLOCKED");
			}
		}
		
		FileOutputStream fo=new FileOutputStream(xlout);
		wb.write(fo);
		wb.close();
		
		
	}

}

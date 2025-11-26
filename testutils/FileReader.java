package nvr.sat.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class has supporting methods to deal with excel files using apache POI library
 * @since : 
 */
public class FileReader {

	static Workbook objWorkbook;
	static Sheet objWorkSheet;
	static Logger logger = LogManager.getLogger(FileReader.class);
	/**
	 * Default constructor
	 * @param strFilePath
	 * @param strSheetName
	 */
	public FileReader(String strFilePath, String strSheetName){
		//String strExcelPath = "./data/TestData.xlsx";
		try {
			FileInputStream objFileStream = new FileInputStream(new File(strFilePath));
			objWorkbook = new XSSFWorkbook(objFileStream);	
			objWorkSheet = objWorkbook.getSheet(strSheetName);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			logger.debug(e.getCause());
			e.printStackTrace();
		}
	}
	/**
	 * This method returns the rowcount in excel file
	 * @author : Ramana Regulapati
	 * @since : 08/22/2020
	 */
	public int getRowCount(){
		int intRows = 0;
		try {
			intRows = objWorkSheet.getPhysicalNumberOfRows();
		}catch (Exception e) {
			logger.debug(e.getMessage());
			logger.debug(e.getCause());
			e.printStackTrace();
		}
		return intRows;
	}
	/**
	 * This method returns the column count
	 * @author :
	 * @since :
	 */
	public int getColumnCount(){
		int intColumns = 0;
		try {
			intColumns = objWorkSheet.getRow(0).getLastCellNum();
		}catch (Exception e) {
			logger.debug(e.getMessage());
			logger.debug(e.getCause());
			e.printStackTrace();
		}
		return intColumns;
	}
	/**
	 * This method returns the given cell value
	 * @author : 
	 * @since : 
	 */
	public Object getCellValue(int intRownNum, int intColumnNum){
		try {
			Object objCellValue = null;
			DataFormatter formatter = new DataFormatter();
			objCellValue = formatter.formatCellValue(objWorkSheet.getRow(intRownNum).getCell(intColumnNum));
			return objCellValue;
		}catch (Exception e) {
			logger.debug(e.getMessage());
			logger.debug(e.getCause());
			e.printStackTrace();
			return null;
		}
	}
}

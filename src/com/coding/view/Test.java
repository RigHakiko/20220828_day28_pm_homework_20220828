package com.coding.view;

import com.coding.mapper.BookMapper;
import com.coding.model.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\g14user01\\OneDrive\\20220630_20210315\\20220828_IO_file\\20220828_day28_pm_test_file\\day28pmTest.xlsx");
        InputStream is = null;


        try {
            //书list
            List<Book> list = new ArrayList<>();

            //读取表格文件, 放入到list中
            is = new FileInputStream(file);
            XSSFWorkbook xw = new XSSFWorkbook(is);
            XSSFSheet xs = xw.getSheetAt(0);
            for (int i = 0; i <= xs.getLastRowNum(); i++) {
                XSSFRow xr = xs.getRow(i);
                Book book = new Book();
//                for (int j = 0; j < xr.getLastCellNum(); j++) {
//                    XSSFCell xc = xr.getCell(j);
//                    switch (xc.getCellType()) {
//                        case STRING: {
//                            System.out.println("String" + xc.getStringCellValue());
//                        }
//                        break;
//                        case NUMERIC: {
//                            System.out.println("number" + xc.getNumericCellValue());
//                        }
//                        break;
//                    }
//                }
                XSSFCell xc = null;
                //第2列
                xc = xr.getCell(1);
                book.setBookName(xc.getStringCellValue());

                //第3列
                xc = xr.getCell(2);
                book.setBookAuthor(xc.getStringCellValue());

                //第4列
                xc = xr.getCell(3);
                book.setBookPrice(xc.getNumericCellValue());

                //添加
                list.add(book);

            }

            //生成Mapper对象

            String resource = "sqlMapConfig.xml";
            SqlSession sqlSession = null;
            InputStream is2 = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is2);
            sqlSession = sqlSessionFactory.openSession();

            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

            //操作数据库
            int result = bookMapper.addBookMany(list);
            System.out.println(result);

            //提交
            sqlSession.commit();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}

package com.video.service.impl;

import com.video.dao.TypeRepository;
import com.video.domain.Type;
import com.video.response.MyException;
import com.video.service.TypeService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAllTypes() {
        List<Type> list = typeRepository.findAll();
        list.sort(new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o1.getTypeId()-o2.getTypeId();
            }
        });
        return list;
    }

    @Override
    public String findTypeById(Integer id) {
        Type type = typeRepository.findById(id).get();
        String typeName = type.getTypeName();
        return typeName;
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception{
            boolean notNull = false;
            List<Type> typeList = new ArrayList<>();
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                throw new MyException("上传文件格式不正确");
            }
            boolean isExcel2003 = true;
            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            InputStream is = file.getInputStream();
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            Sheet sheet = wb.getSheetAt(0);
            if(sheet!=null){
                notNull = true;
            }
            Type type;
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
                Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
                if (row == null){
                    continue;
                }
                //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
                type = new Type();
                if( row.getCell(1).getCellType() !=1){//循环时，得到每一行的单元格进行判断
                    throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
                }
                String typeName = row.getCell(1).getStringCellValue();//得到每一行第一个单元格的值
                if(typeName == null || typeName.isEmpty()){//判断是否为空
                    throw new MyException("导入失败(第"+(r+1)+"行,用户名未填写)");
                }
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
                String typeInfo = row.getCell(1).getStringCellValue();
                if(typeInfo==null || typeInfo.isEmpty()){
                    throw new MyException("导入失败(第"+(r+1)+"行,密码未填写)");
                }
                //完整的循环一次 就组成了一个对象
                type.setTypeName(typeName);
                type.setTypeInfo(typeInfo);
                typeList.add(type);
            }
            for (Type type1 : typeList) {
                String typeName = type1.getTypeName();
                if (typeRepository.findAllByTypeName(typeName)!=null) {
                    Type save = typeRepository.save(type1);
                    System.out.println(" 插入 "+save);
                } else {
                    Type type2 = typeRepository.saveAndFlush(type1);
                    System.out.println(" 更新 "+type2);
                }
            }
            return notNull;
        }
}

/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.action.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParticipantsAction extends BaseAction {

  private static final long serialVersionUID = 1L;

  private static final String[] HEAD_TEMPLATE = {"Code", "Name", "Last Name", "Gender", "Citizenship",
    "Country of residence", "Highest degree", "Institution", "email", "Reference", "Fellowship"};


  private InputStream inputStream;


  private String fileName;

  @Inject
  public ParticipantsAction(APConfig config) {
    super(config);
  }


  public String dowmloadTemplate() {
    System.out.println("dowmloadTemplate");

    final Workbook wb = new XSSFWorkbook();
    final Sheet participants = wb.createSheet("Participants");
    final Row row = participants.createRow(0);
    for (int i = 0; i < HEAD_TEMPLATE.length; i++) {
      final Cell cell = row.createCell(i);
      cell.setCellValue(HEAD_TEMPLATE[i]);

    }

    // final FileOutputStream fileOut;

    try {
      // fileOut = new FileOutputStream("C:/Users/logonzalez/Downloads/workbook.xlsx");
      final ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
      wb.write(fileOut);
      wb.close();

      inputStream = new ByteArrayInputStream(fileOut.toByteArray());

    } catch (final FileNotFoundException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }


    return SUCCESS;


  }


  public String getFileName() {
    return fileName;
  }


  public InputStream getInputStream() {
    return inputStream;
  }


  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }


}

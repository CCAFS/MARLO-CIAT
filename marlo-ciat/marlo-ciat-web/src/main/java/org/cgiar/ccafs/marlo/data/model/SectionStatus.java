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
package org.cgiar.ccafs.marlo.data.model;


public class SectionStatus implements java.io.Serializable {


  private static final long serialVersionUID = -5830242271701358980L;


  private Long id;

  private ResearchProgram researchProgram;


  private ResearchOutcome researchOutcome;
  private ResearchOutput researchOutput;
  private String sectionName;
  private String missingFields;
  private String cycle;
  private Integer year;

  public SectionStatus() {
  }

  public SectionStatus(ResearchProgram researchProgram, ResearchOutcome researchOutcome, ResearchOutput researchOutput,
    String sectionName, String missingFields, String cycle, Integer year) {
    this.researchProgram = researchProgram;
    this.researchOutcome = researchOutcome;
    this.researchOutput = researchOutput;
    this.sectionName = sectionName;
    this.missingFields = missingFields;
    this.cycle = cycle;
    this.year = year;
  }

  public SectionStatus(String sectionName) {
    this.sectionName = sectionName;
  }

  public String getCycle() {
    return this.cycle;
  }

  public Long getId() {
    return this.id;
  }

  public String getMissingFields() {
    return this.missingFields;
  }

  public ResearchOutcome getResearchOutcome() {
    return researchOutcome;
  }


  public ResearchOutput getResearchOutput() {
    return researchOutput;
  }

  public ResearchProgram getResearchProgram() {
    return researchProgram;
  }

  public String getSectionName() {
    return this.sectionName;
  }

  public Integer getYear() {
    return this.year;
  }

  public void setCycle(String cycle) {
    this.cycle = cycle;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setMissingFields(String missingFields) {
    this.missingFields = missingFields;
  }

  public void setResearchOutcome(ResearchOutcome researchOutcome) {
    this.researchOutcome = researchOutcome;
  }

  public void setResearchOutput(ResearchOutput researchOutput) {
    this.researchOutput = researchOutput;
  }

  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public void setYear(Integer year) {
    this.year = year;
  }


}


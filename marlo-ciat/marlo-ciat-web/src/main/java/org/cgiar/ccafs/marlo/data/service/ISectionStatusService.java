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
package org.cgiar.ccafs.marlo.data.service;

import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.service.impl.SectionStatusService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(SectionStatusService.class)
public interface ISectionStatusService {


  /**
   * This method removes a specific sectionStatus value from the database.
   * 
   * @param sectionStatusId is the sectionStatus identifier.
   * @return true if the sectionStatus was successfully deleted, false otherwise.
   */
  public boolean deleteSectionStatus(long sectionStatusId);


  /**
   * This method gets a list of sectionStatus to verify if the program have all the sections
   * 
   * @param programID - the program id
   * @return List of SectionStatuss or null if the user is invalid or not have roles.
   */
  public List<String> distinctSectionStatus(long programID);

  /**
   * This method gets a list of sectionStatus to verify if the project have all the sections
   * 
   * @param projectID - the project id
   * @return List of SectionStatuss or null if the user is invalid or not have roles.
   */
  public List<String> distinctSectionStatusProject(long projectID);


  /**
   * This method validate if the sectionStatus identify with the given id exists in the system.
   * 
   * @param sectionStatusID is a sectionStatus identifier.
   * @return true if the sectionStatus exists, false otherwise.
   */
  public boolean existSectionStatus(long sectionStatusID);


  /**
   * This method gets a list of sectionStatus that are active
   * 
   * @return a list from SectionStatus null if no exist records
   */
  public List<SectionStatus> findAll();

  /**
   * gets a SectionStatus of the one section by a specific research program
   * 
   * @param programId
   * @param sectionName
   * @return a SectionStatus List Object
   */
  public List<SectionStatus> getSectionStatus(long programId, String sectionName);

  /**
   * gets a SectionStatus of the one section by a specific deliverable
   * 
   * @param deliverableId
   * @param projectId
   * @param sectionName
   * @return a SectionStatus Object
   */
  public SectionStatus getSectionStatusByDeliverable(long deliverableId, long projectId, String sectionName, int year);

  /**
   * This method gets a sectionStatus object by a given sectionStatus identifier.
   * 
   * @param sectionStatusID is the sectionStatus identifier.
   * @return a SectionStatus object.
   */
  public SectionStatus getSectionStatusById(long sectionStatusID);

  /**
   * gets a SectionStatus of the one section by a specific research program
   * 
   * @param programId
   * @param outcomeId
   * @param sectionName
   * @return a SectionStatus Object
   */
  public SectionStatus getSectionStatusByOutcome(long programId, long outcomeId, String sectionName, int year);

  /**
   * gets a SectionStatus of the one section by a specific research program
   * 
   * @param programId
   * @param outputId
   * @param sectionName
   * @return a SectionStatus Object
   */
  public SectionStatus getSectionStatusByOutput(long programId, long outputId, String sectionName, int year);

  /**
   * gets a SectionStatus of the one section by a specific research program
   * 
   * @param programId
   * @param sectionName
   * @return a SectionStatus Object
   */
  public SectionStatus getSectionStatusByProgram(long programId, String sectionName, int year);

  /**
   * gets a SectionStatus of the one section by a specific project
   * 
   * @param programId
   * @param projectId
   * @param sectionName
   * @return a SectionStatus Object
   */
  public SectionStatus getSectionStatusByProject(long programId, long projectId, String sectionName, int year);

  /**
   * This method gets a list of sectionStatuss belongs of the user
   * 
   * @param userId - the user id
   * @return List of SectionStatuss or null if the user is invalid or not have roles.
   */
  public List<SectionStatus> getSectionStatussByUserId(Long userId);

  /**
   * This method saves the information of the given sectionStatus
   * 
   * @param sectionStatus - is the sectionStatus object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the sectionStatus was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveSectionStatus(SectionStatus sectionStatus);


}
